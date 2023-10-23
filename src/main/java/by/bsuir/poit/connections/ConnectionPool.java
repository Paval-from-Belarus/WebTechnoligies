package by.bsuir.poit.connections;

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
public static final Logger logger = LogManager.getLogger(ConnectionPool.class);
private static final String CLOSE_METHOD_NAME = "close";

public static class ConnectionPoolException extends DataAccessException {
      public ConnectionPoolException(String message) {
	    super(message);
      }

      public ConnectionPoolException(Throwable t) {
	    super(t);
      }
}

public ConnectionPool(ConnectionConfig config) throws ConnectionPoolException {
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
		      Connection.class.getInterfaces(),
		      (proxy, method, args) -> {
			    if (method.getName().equals(CLOSE_METHOD_NAME)) {
				  proxyConnections.add((Connection) proxy);
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
      this.proxyConnections = new CopyOnWriteArrayList<>(proxyConnections);
      this.originConnections = Collections.unmodifiableList(originConnections);
}

public Connection getConnection() {
      if (proxyConnections.isEmpty()) { //no more available
	    throw new ConnectionPoolException("The pool is exhausted");
      }
      return proxyConnections.remove(proxyConnections.size() - 1);
}

@Override
public void close() {
      ConnectionPoolException lastException = null;
      for (Connection connection : originConnections) {
	    try {
		  connection.close();
	    } catch (SQLException e) {
		  logger.error("Connection pool closing exception: " + e.getMessage() + " SQLState: " + e.getSQLState());
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
