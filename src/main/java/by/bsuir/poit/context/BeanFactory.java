package by.bsuir.poit.context;

import by.bsuir.poit.context.exception.BeanFactoryException;
import by.bsuir.poit.dao.connections.ConnectionConfig;
import by.bsuir.poit.dao.connections.ConnectionPool;
import jakarta.inject.Named;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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
public static final String MAPPER_BEANS_PACKAGE = "mappers-beans-package";
public static final String DAO_BEANS_PACKAGE = "dao-beans-package";
public static final String SERVICE_BEANS_PACKAGE = "service-beans-package";
public static final int DEFAULT_POOL_SIZE = 10;

@Override
public void contextInitialized(ServletContextEvent contextEvent) {
      instanceMap.clear();
      ServletContext context = contextEvent.getServletContext();
      List<String> requiredParams = List.of(JDBC_URL, DATABASE_PASSWORD, DATABASE_USER, DATABASE_PASSWORD, JDBC_DRIVER_NAME,
	  CONNECTION_POOL_SIZE, MAPPER_BEANS_PACKAGE, DAO_BEANS_PACKAGE, SERVICE_BEANS_PACKAGE);
      Map<String, String> params = requiredParams.stream()
				       .map(parameter -> Map.entry(parameter, context.getInitParameter(parameter)))
				       .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
      int poolSize = params.get(CONNECTION_POOL_SIZE) == null ? DEFAULT_POOL_SIZE : Integer.parseInt(params.get(CONNECTION_POOL_SIZE));
      if (params.values().stream().anyMatch(Objects::isNull)) {
	    throw newBeanFactoryException("Impossible to create bean factory without required parameters. Check web.xml file context-params");
      }
      ConnectionConfig config = ConnectionConfig.builder()
				    .maxPoolSize(poolSize)
				    .driverClassName(params.get(JDBC_DRIVER_NAME))
				    .jdbcUrl(params.get(JDBC_URL))
				    .user(params.get(DATABASE_USER))
				    .password(params.get(DATABASE_PASSWORD))
				    .build();
      ConnectionPool pool = new ConnectionPool(config);
      context.setAttribute(ConnectionPool.class.getName(), pool);
      initDaoBeans(pool, params.get(DAO_BEANS_PACKAGE), params.get(MAPPER_BEANS_PACKAGE));
      initServiceBeans(params.get(SERVICE_BEANS_PACKAGE));
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

///Find classses with specific annotation and map them to <code>Map.entry<Interface, Class></code>
///If class have more than one interface, then self-class will be chosen as target
private Map<Class<?>, Class<?>> findTypesWithInterface(Reflections scanner, Class<? extends Annotation> annotation) {
      return scanner.getTypesAnnotatedWith(annotation).stream()
		 .map(clazz -> {
		       Class<?>[] interfaces = clazz.getInterfaces();
		       if (interfaces.length == 1) {
			     return Map.entry(interfaces[0], clazz);
		       }
		       return Map.entry(clazz, clazz);
		 })
		 .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
}

private void initDaoBeans(ConnectionPool pool, String daoBeansPackage, String mapperBeanPackage) {
      Reflections scanner = new Reflections(daoBeansPackage, mapperBeanPackage);
      instanceMap.put(ConnectionPool.class, pool);
      putBeansInMap(findTypesWithInterface(scanner, Named.class));
      putBeansInMap(findTypesWithInterface(scanner, Repository.class));
}

private void initServiceBeans(String serviceBeanPackage) {
      Reflections scanner = new Reflections(serviceBeanPackage);
      putBeansInMap(findTypesWithInterface(scanner, Service.class));
}

private void putBeansInContext(ServletContext context) {
      for (Map.Entry<Class<?>, Object> entry : instanceMap.entrySet()) {
	    context.setAttribute(entry.getKey().getName(), entry.getValue());
      }
}

@SneakyThrows
private void putBeansInMap(Map<Class<?>, Class<?>> classSet) {
      for (Map.Entry<Class<?>, Class<?>> entry : classSet.entrySet()) {
	    Class<?> targetClass = entry.getKey();
	    Class<?> implementationClass = entry.getValue();
	    Constructor<?>[] constructors = implementationClass.getDeclaredConstructors();
	    Constructor<?> chosen = constructors[0];//at least a single constructor is present in class
	    if (constructors.length > 1) {
		  constructors = Arrays.stream(constructors)
				     .filter(constructor -> constructor.isAnnotationPresent(Autowired.class))
				     .toArray(Constructor<?>[]::new);
		  if (constructors.length > 1) {
			chosen = null;
		  }
	    }
	    if (chosen == null) {
		  throw newBeanFactoryException("Too many constructor for bean={}", implementationClass.getName());
	    }
	    Object[] parameters = Arrays.stream(chosen.getParameters())
				      .map(parameter -> findBeanInMap(parameter.getType()))
				      .toArray();
	    chosen.setAccessible(true);
	    Object bean = chosen.newInstance(parameters);
	    instanceMap.put(targetClass, bean);
      }
}


@SuppressWarnings("unchecked")
private <T> T findBeanInMap(Class<T> clazz) {
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

private static BeanFactoryException newBeanFactoryException(String cause, Object... args) {
      LOGGER.error(cause, args);
      return new BeanFactoryException(cause);
}
}
