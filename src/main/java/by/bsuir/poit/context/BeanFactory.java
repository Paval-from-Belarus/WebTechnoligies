package by.bsuir.poit.context;

import by.bsuir.poit.context.exception.BeanFactoryException;
import by.bsuir.poit.dao.*;
import by.bsuir.poit.dao.connections.ConnectionConfig;
import by.bsuir.poit.dao.connections.ConnectionPool;
import by.bsuir.poit.dao.impl.*;
import by.bsuir.poit.bean.mappers.*;
import by.bsuir.poit.services.AuthorizationService;
import by.bsuir.poit.services.impl.AuthorizationServiceImpl;
import by.bsuir.poit.servlets.command.RequestHandlerProvider;
import by.bsuir.poit.servlets.command.impl.RequestHandlerProviderImpl;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Paval Shlyk
 * @since 15/09/2023
 */
@WebListener
@NoArgsConstructor
public class BeanFactory implements ServletContextListener {
public static final Logger LOGGER = LogManager.getLogger(BeanFactory.class);
public static final String JDBC_URL = "jdbc-url";
public static final String DATABASE_USER = "database-user";
public static final String DATABASE_PASSWORD = "database-password";
public static final String JDBC_DRIVER_NAME = "driver-class-name";
public static final String CONNECTION_POOL_SIZE = "pool-size";
public static final String DAO_BEANS_PACKAGE = "dao-beans-package";
public static final String SERVICE_BEANS_PACKAGE = "service-beans-package";
public static final int DEFAULT_POOL_SIZE = 10;

@Override
public void contextInitialized(ServletContextEvent contextEvent) {
      instanceMap.clear();
      ServletContext context = contextEvent.getServletContext();
      String jdbcUrl = context.getInitParameter(JDBC_URL);
      String user = context.getInitParameter(DATABASE_USER);
      String password = context.getInitParameter(DATABASE_PASSWORD);
      String driverClassName = context.getInitParameter(JDBC_DRIVER_NAME);
      String poolSizeLiteral = context.getInitParameter(CONNECTION_POOL_SIZE);
      if (jdbcUrl == null || user == null || password == null || driverClassName == null) {
	    throw newBeanFactoryException("Impossible to create bean factory without required parameters. Check web.xml file context-params");
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
      initDaoBeans(pool);
      initServiceBeans();
      putBeansInContext(context);
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

private void initDaoBeans(ConnectionPool pool) {
      Map<Class<?>, Object> daoBeans = new HashMap<>();
      daoBeans.put(AuctionBetDao.class, new AuctionBetDaoImpl(pool, AuctionBetMapper.INSTANCE));
      daoBeans.put(AuctionDao.class, new AuctionDaoImpl(pool, AuctionMapper.INSTANCE));
      daoBeans.put(UserDao.class, new UserDaoImpl(pool, UserMapper.INSTANCE));
      daoBeans.put(ClientDao.class, new ClientDaoImpl(pool, ClientMapper.INSTANCE));
      daoBeans.put(AuctionTypeDao.class, new AuctionTypeDaoImpl(pool, AuctionTypeMapper.INSTANCE));
      daoBeans.put(LotDao.class, new LotDaoImpl(pool, LotMapper.INSTANCE));
      daoBeans.put(DeliveryPointDao.class, new DeliveryPointDaoImpl(pool, DeliveryPointMapper.INSTANCE));
      daoBeans.put(ClientFeedbackDao.class, new ClientFeedbackDaoImpl(pool, ClientFeedbackMapper.INSTANCE));
      instanceMap.putAll(daoBeans);
}

private void initServiceBeans() {
      Map<Class<?>, Object> serviceBeans = new HashMap<>();
      serviceBeans.put(RequestHandlerProvider.class, new RequestHandlerProviderImpl());
      serviceBeans.put(AuthorizationService.class, new AuthorizationServiceImpl(findBean(UserDao.class)));
      instanceMap.putAll(serviceBeans);
}

private void putBeansInContext(ServletContext context) {
      for (Map.Entry<Class<?>, Object> entry : instanceMap.entrySet()) {
	    context.setAttribute(entry.getKey().getName(), entry.getValue());
      }
}

@SuppressWarnings("unchecked")
private <T> T findBean(Class<T> clazz) {
      T bean;
      try {
	    bean = (T) instanceMap.get(clazz);
      } catch (Exception e) {
	    final String msg = String.format("Failed to fetch bean by class=%s", clazz);
	    LOGGER.error(msg);
	    throw new IllegalStateException(msg);
      }
      if (bean == null) {
	    throw newBeanFactoryException(String.format("Failed for find existing bean by class=%s", clazz));
      }
      return bean;
}

private final Map<Class<?>, Object> instanceMap = new ConcurrentHashMap<>();

private static BeanFactoryException newBeanFactoryException(String cause) {
      LOGGER.error(cause);
      return new BeanFactoryException(cause);
}
}
