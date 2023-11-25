package by.bsuir.poit.dao.connections;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Paval Shlyk
 * @since 15/09/2023
 */
public class ConnectionPool implements AutoCloseable {
public static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
private static final String CLOSE_METHOD_NAME = "close";
public ConnectionPool(ConnectionConfig config) throws ConnectionPoolException {
      this.proxyConnections = new CopyOnWriteArrayList<>();
      List<Connection> proxyConnections = new ArrayList<>(config.getMaxPoolSize());
      List<Connection> originConnections = new ArrayList<>(config.getMaxPoolSize());
      try {
	    Class.forName(config.getDriverClassName());
      } catch (ClassNotFoundException e) {
	    throw new ConnectionPoolException("Driver is not found " + config.getDriverClassName());
      }
      try {
	    for (int i = 0; i < config.getMaxPoolSize(); i++) {
		  Connection originConnection = DriverManager.getConnection(config.getJdbcUrl(), config.getUser(), config.getPassword());
		  Connection proxyConnection = (Connection) Proxy.newProxyInstance(Connection.class.getClassLoader(),
		      new Class<?>[]{Connection.class},
		      (proxy, method, args) -> {
			    if (method.getName().equals(CLOSE_METHOD_NAME)) {
				  LOGGER.trace("Connection {} is returned", proxy.toString());
				  this.proxyConnections.add((Connection) proxy);
				  return null;
			    }
			    return method.invoke(originConnection, args);
		      });
		  originConnections.add(originConnection);
		  proxyConnections.add(proxyConnection);
	    }
      } catch (SQLException e) {
	    throw new ConnectionPoolException(e);
      }
      this.proxyConnections.addAll(proxyConnections);
      this.originConnections = Collections.unmodifiableList(originConnections);
}

public Connection getConnection() {
      if (proxyConnections.isEmpty()) { //no more available
	    throw new ConnectionPoolException("The pool is exhausted");
      }
      Connection connection = proxyConnections.remove(proxyConnections.size() - 1);
      LOGGER.trace("The connection {} is acquired", connection.toString());
      return connection;
}

@Override
public void close() {
      ConnectionPoolException lastException = null;
      for (Connection connection : originConnections) {
	    try {
		  connection.close();
	    } catch (SQLException e) {
		  LOGGER.error("Connection pool closing exception: " + e.getMessage() + " SQLState: " + e.getSQLState());
		  if (lastException == null) {
			lastException = new ConnectionPoolException(e);
		  }
	    }
      }
      if (lastException != null) {
	    throw lastException;
      }
}

private final List<Connection> proxyConnections;
private final List<Connection> originConnections; //the real connections
}
