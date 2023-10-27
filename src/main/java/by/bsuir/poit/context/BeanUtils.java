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

/**
 * @author Paval Shlyk
 * @since 27/10/2023
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanUtils {
private static final Logger LOGGER = LogManager.getLogger(BeanUtils.class);

@SneakyThrows
public static <T> void configure(T instance, ServletContext context) {
      Field[] fields = instance.getClass().getDeclaredFields();
      for (Field field : fields) {
	    Autowired annotation = field.getAnnotation(Autowired.class);
	    if (annotation == null) {
		  continue;
	    }
	    String fieldClassName = field.getType().getName();
	    Object fieldValue = context.getAttribute(fieldClassName);
	    if (fieldValue == null) {
		  final String msg = "Impossible to fetch bean with className=" + fieldClassName + " from servlet context";
		  LOGGER.error(msg);
		  throw new IllegalStateException(msg);
	    }
	    field.setAccessible(true);
	    field.set(instance, fieldValue);
      }
}

public static <T> void configureFilter(T instance, FilterConfig config) {
      BeanUtils.configure(instance, config.getServletContext());
}

public static <T> void configureServlet(T instance, ServletConfig config) {
      BeanUtils.configure(instance, config.getServletContext());
}
}
