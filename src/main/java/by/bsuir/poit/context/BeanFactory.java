package by.bsuir.poit.context;

import by.bsuir.poit.connections.ConnectionConfig;
import by.bsuir.poit.connections.ConnectionPool;
import by.bsuir.poit.dao.AuctionDao;
import by.bsuir.poit.dao.UserDao;
import by.bsuir.poit.dao.mappers.AuctionMapper;
import by.bsuir.poit.dao.mappers.UserMapper;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Paval Shlyk
 * @since 15/09/2023
 */
@WebListener
@NoArgsConstructor
public class BeanFactory implements ServletContextListener {
public static final Logger logger = LogManager.getLogger(BeanFactory.class);
public static final String JDBC_URL = "jdbc-url";
public static final String DATABASE_USER = "database-user";
public static final String DATABASE_PASSWORD = "database-password";
public static final String JDBC_DRIVER_NAME = "driver-class-name";
public static final String CONNECTION_POOL_SIZE = "pool-size";
public static final String DAO_BEANS_PACKAGE = "dao-beans-package";
public static final int DEFAULT_POOL_SIZE = 10;
@Override
public void contextInitialized(ServletContextEvent contextEvent) {
      ServletContext context = contextEvent.getServletContext();
      String jdbcUrl = context.getInitParameter(JDBC_URL);
      String user = context.getInitParameter(DATABASE_USER);
      String password = context.getInitParameter(DATABASE_PASSWORD);
      String driverClassName = context.getInitParameter(JDBC_DRIVER_NAME);
      String poolSizeLiteral = context.getInitParameter(CONNECTION_POOL_SIZE);
      if (jdbcUrl == null || user == null || password == null || driverClassName == null) {
	    final String msg = "Impossible to create bean factory without required parameters. Check web.xml file context-params";
	    logger.error(msg);
	    throw new IllegalStateException(msg);
      }
      int poolSize = poolSizeLiteral != null ? Integer.parseInt(poolSizeLiteral) : DEFAULT_POOL_SIZE;
      ConnectionConfig config = ConnectionConfig.builder()
				    .maxPoolSize(poolSize)
				    .driverClassName(driverClassName)
				    .jdbcUrl(jdbcUrl)
				    .user(user)
				    .password(password)
				    .build();
      ConnectionPool pool = new ConnectionPool(config);
      context.setAttribute(ConnectionPool.class.getName(), pool);
      initDaoBeans(context, pool);
}


@Override
public void contextDestroyed(ServletContextEvent contextEvent) {
      ServletContext context = contextEvent.getServletContext();
      ConnectionPool pool = (ConnectionPool) context.getAttribute(ConnectionPool.class.getName());
      if (pool != null) {
	    pool.close();
      }
      //it's not obligatory to remove dao beans because they will be removed during the next context update
}
private void initDaoBeans(ServletContext context, ConnectionPool pool) {
      UserDao userDao = new UserDao(pool, UserMapper.INSTANCE);
      AuctionDao auctionDao = new AuctionDao(pool, AuctionMapper.INSTANCE);

      context.setAttribute(UserDao.class.getName(), userDao);
}
}
