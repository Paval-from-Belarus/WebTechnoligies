package by.bsuir.poit.context;

/**
 * The annotation should be marked under {@link java.util.Map} field in any bean (available to scan by {@link BeanFactory}) for holding {@link by.bsuir.poit.servlets.command.RequestHandler} implementation by given url.
 * Url is specified by {@link RequestHandlerDefinition}
 *
 * @author Paval Shlyk
 * @since 06/11/2023
 */
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface RequestHandlerMap {
}
