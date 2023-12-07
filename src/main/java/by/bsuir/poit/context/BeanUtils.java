package by.bsuir.poit.context;

import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.function.Function;

/**
 * This class provides utility methods for configuring beans with different contexts.
 *
 * @author Paval Shlyk
 * @since 27/10/2023
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanUtils {
private static final Logger LOGGER = LogManager.getLogger(BeanUtils.class);

/**
 * Configures the given instance with the provided ServletContext.
 * Uses a supplier to fetch the bean from the context.
 *
 * @param instance the instance to be configured
 * @param context the ServletContext
 */
public static <T> void configureWithContext(T instance, ServletContext context) {
      configureWithSupplier(instance, (clazz) -> context.getAttribute(clazz.getName()));
}
/**
 * Configures the given instance with the provided bean map.
 * Uses a supplier to fetch the bean from the map.
 *
 * @param instance the instance to be configured
 * @param beanMap the bean map
 */
public static <T> void configureWithMap(T instance, Map<Class<?>, Object> beanMap) {
      configureWithSupplier(instance, beanMap::get);
}
/**
 * Configures the given instance with the provided bean supplier.
 * Uses reflection to set the field values of the instance.
 *
 * @param instance the instance to be configured
 * @param beanSupplier the bean supplier
 */
@SneakyThrows
private static <T> void configureWithSupplier(T instance, Function<Class<?>, Object> beanSupplier) {
      Field[] fields = instance.getClass().getDeclaredFields();
      for (Field field : fields) {
	    Autowired annotation = field.getAnnotation(Autowired.class);
	    if (annotation == null) {
		  continue;
	    }
	    Object fieldValue = beanSupplier.apply(field.getType());
	    if (fieldValue == null) {
		  final String msg = "Impossible to fetch bean with className=" + field.getType().getName() + " from servlet context";
		  LOGGER.error(msg);
		  throw new IllegalStateException(msg);
	    }
	    field.setAccessible(true);
	    field.set(instance, fieldValue);
      }
}
/**
 * Configures the given instance with the provided FilterConfig.
 * Uses the ServletContext from the FilterConfig.
 *
 * @param instance the instance to be configured
 * @param config the FilterConfig
 */
public static <T> void configureFilter(T instance, FilterConfig config) {
      BeanUtils.configureWithContext(instance, config.getServletContext());
}
/**
 * Configures the given instance with the provided ServletConfig.
 * Uses the ServletContext from the ServletConfig.
 *
 * @param instance the instance to be configured
 * @param config the ServletConfig
 */
public static <T> void configureServlet(T instance, ServletConfig config) {
      BeanUtils.configureWithContext(instance, config.getServletContext());
}
}
