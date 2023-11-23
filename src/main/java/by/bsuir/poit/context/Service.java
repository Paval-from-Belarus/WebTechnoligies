package by.bsuir.poit.context;

/**
 * Stereotype annotation for each service bean.
 * Analog to Spring {@code @Service} annotation
 * @author Paval Shlyk
 * @since 27/10/2023
 */
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface Service {
/**
 * The field is not currently supported.
 * But it's supposed to be used to inject in {@link RequestHandlerMap} by name (now by Class name)
 * @return the service name
 */
String value() default "";
}
