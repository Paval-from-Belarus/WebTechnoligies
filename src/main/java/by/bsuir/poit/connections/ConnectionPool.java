package by.bsuir.poit.connections;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Paval Shlyk
 * @since 15/09/2023
 */
public class ConnectionPool {
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
      List<Connection> connections = new ArrayList<>(config.getMaxPoolSize());
      try {
	    Class.forName(config.getDriverClassName());
      } catch (ClassNotFoundException e) {
	    throw new ConnectionPoolException("Driver is not found " + config.getDriverClassName());
      }
      try {
	    for (int i = 0; i < config.getMaxPoolSize(); i++) {
		  Connection connection = DriverManager.getConnection(config.getJdbcUrl(), config.getUser(), config.getPassword());
		  Proxy.newProxyInstance(Connection.class.getClassLoader(), Connection.class.getInterfaces(), this::connectionProxyInvocationHandler);
		  connections.add(connection);
	    }
      } catch (SQLException e) {
	    throw new ConnectionPoolException(e);
      }
      this.connections = new CopyOnWriteArrayList<>(connections);
}

private Object connectionProxyInvocationHandler(Object proxy, Method method, Object[] args) throws Throwable {
      Object result;
      if (method.getName().equals(CLOSE_METHOD_NAME)) {
	    result = connections.add((Connection) proxy);
      } else {
	    result = method.invoke(proxy, args);
      }
      return result;
}

public Connection getConnection() {
      if (connections.isEmpty()) { //no more available
	    throw new ConnectionPoolException("The pool is exhaused");
      }
      return null;
}

private final List<Connection> connections;
private final Logger logger = LogManager.getLogger();
}
