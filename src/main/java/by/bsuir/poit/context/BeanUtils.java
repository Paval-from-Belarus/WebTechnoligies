package by.bsuir.poit.context;

import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Paval Shlyk
 * @since 27/10/2023
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanUtils {
private static final Logger LOGGER = LogManager.getLogger(BeanUtils.class);

public static <T> void configureWithContext(T instance, ServletContext context) {
      configureWithSupplier(instance, (clazz) -> context.getAttribute(clazz.getName()));
}

public static <T> void configureWithMap(T instance, Map<Class<?>, Object> beanMap) {
      configureWithSupplier(instance, beanMap::get);
}

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

public static <T> void configureFilter(T instance, FilterConfig config) {
      BeanUtils.configureWithContext(instance, config.getServletContext());
}

public static <T> void configureServlet(T instance, ServletConfig config) {
      BeanUtils.configureWithContext(instance, config.getServletContext());
}
}
