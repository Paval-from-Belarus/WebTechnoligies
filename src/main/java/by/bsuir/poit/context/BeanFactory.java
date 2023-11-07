package by.bsuir.poit.context;

import by.bsuir.poit.context.exception.BeanFactoryException;
import by.bsuir.poit.context.exception.BeanNotFoundException;
import by.bsuir.poit.dao.connections.ConnectionConfig;
import by.bsuir.poit.dao.connections.ConnectionPool;
import by.bsuir.poit.servlets.command.RequestHandler;
import by.bsuir.poit.servlets.command.RequestHandlerProvider;
import by.bsuir.poit.servlets.command.RequestMethod;
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
import java.lang.reflect.Field;
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
      List<String> requiredParams = List.of(JDBC_URL, DATABASE_USER, DATABASE_PASSWORD, JDBC_DRIVER_NAME,
	  CONNECTION_POOL_SIZE, MAPPER_BEANS_PACKAGE, DAO_BEANS_PACKAGE, SERVICE_BEANS_PACKAGE);
      Map<String, String> params = requiredParams.stream()
				       .map(parameter -> Map.entry(parameter, context.getInitParameter(parameter)))
				       .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
      int poolSize = params.get(CONNECTION_POOL_SIZE) == null ? DEFAULT_POOL_SIZE : Integer.parseInt(params.get(CONNECTION_POOL_SIZE));
      params.remove(CONNECTION_POOL_SIZE);
      if (params.values().stream().anyMatch(Objects::isNull)) {
	    String msg = "Impossible to create bean factory without required parameters. Check web.xml file context-params";
	    LOGGER.error(msg);
	    throw newBeanFactoryException(msg);
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
///if several classes will have same interface then both will be chosend as target
private Map<Class<?>, Class<?>> findTypesWithInterface(Reflections scanner, Class<? extends Annotation> annotation) {
      Map<Class<?>, Class<?>> interfaceTargetMap = new HashMap<>();
      scanner.getTypesAnnotatedWith(annotation).stream()
	  .map(clazz -> {
		Class<?>[] interfaces = clazz.getInterfaces();
		if (interfaces.length == 1) {
		      return Map.entry(interfaces[0], clazz);
		}
		return Map.entry(clazz, clazz);
	  })
	  .forEach(classWithInterface -> {
		if (!interfaceTargetMap.containsKey(classWithInterface.getKey())) {
		      interfaceTargetMap.put(classWithInterface.getKey(), classWithInterface.getValue());
		} else {
		      Class<?> existingImpl = interfaceTargetMap.get(classWithInterface.getKey());
		      interfaceTargetMap.put(classWithInterface.getKey(), null);
		      assert existingImpl != null;
		      interfaceTargetMap.put(existingImpl, existingImpl);
		      interfaceTargetMap.put(classWithInterface.getValue(), classWithInterface.getValue());
		}
	  });
      HashMap<Class<?>, Class<?>> outputMap = new HashMap<>();
      for (Map.Entry<Class<?>, Class<?>> entry : interfaceTargetMap.entrySet()) {
	    if (entry.getValue() != null) {
		  outputMap.put(entry.getKey(), entry.getValue());
	    }
      }
      return outputMap;
}

private void initDaoBeans(ConnectionPool pool, String daoBeansPackage, String mapperBeanPackage) {
      Reflections scanner = new Reflections(daoBeansPackage, mapperBeanPackage);
      instanceMap.put(ConnectionPool.class, pool);
      putBeansInMap(findTypesWithInterface(scanner, Named.class));//jakarta mappers beans
      putBeansInMap(findTypesWithInterface(scanner, Repository.class));
}

private void initServiceBeans(String serviceBeanPackage) {
      Reflections scanner = new Reflections(serviceBeanPackage);
      putBeansInMap(findTypesWithInterface(scanner, Service.class));
      putBeansInMap(findTypesWithInterface(scanner, RequestHandlerDefinition.class));//handler should be passed after services
      RequestHandlerProvider provider = (RequestHandlerProvider) instanceMap.get(RequestHandlerProvider.class);
      if (provider == null) {
	    throw newBeanNotFoundException("Failed to found RequestHandlerProvider bean. Impossible to start application");
      }
      configureRequestHandlerProvider(provider);
}

private void configureRequestHandlerProvider(RequestHandlerProvider provider) {
      Map<String, RequestHandler> handlerMap = newHandlerMap();
      Field[] fields = provider.getClass().getDeclaredFields();
      for (Field field : fields) {
	    if (!field.isAnnotationPresent(RequestHandlerMap.class)) {
		  continue;
	    }
	    field.setAccessible(true);
	    try {
		  field.set(provider, handlerMap);
		  LOGGER.info("RequestProvider is configured");
		  return;
	    } catch (Exception e) {
		  throw newBeanFactoryException("Failed to create RequestProvider bean by cause=%s", e.getCause());
	    }
      }
}

private Map<String, RequestHandler> newHandlerMap() {
      Map<String, RequestHandler> urlMap = new HashMap<>();
      instanceMap.values().stream()
	  .filter(object -> object instanceof RequestHandler)
	  .forEach(handler -> {
		RequestHandlerDefinition annotation = handler.getClass().getAnnotation(RequestHandlerDefinition.class);
		for (String url : annotation.urlPatterns()) {
		      if (!url.startsWith("/")) {
			    url = "/" + url;
		      }
		      urlMap.put(url, (RequestHandler) handler);
		}
	  });
      return urlMap;
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
	    if (implementationClass.isInterface()) {
		  continue;
	    }
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
		  throw newBeanFactoryException("Too many constructor for bean=%s", implementationClass.getName());
	    }
	    Object[] parameters = Arrays.stream(chosen.getParameters())
				      .map(parameter -> findBeanInMap(parameter.getType()))
				      .toArray();
	    chosen.setAccessible(true);
	    Object bean = chosen.newInstance(parameters);
	    LOGGER.trace("Bean {} by interface {} was created",
		bean.getClass().getName(),
		entry.getKey().getName()
	    );
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
      String msg = String.format(cause, args);
      LOGGER.error(msg);
      return new BeanFactoryException(msg);
}

private static BeanNotFoundException newBeanNotFoundException(String cause, Object... args) {
      String msg = String.format(cause, args);
      LOGGER.error(msg);
      throw new BeanNotFoundException(msg);
}
}
