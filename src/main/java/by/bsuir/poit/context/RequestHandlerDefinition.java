package by.bsuir.poit.context;

import by.bsuir.poit.servlets.command.RequestMethod;
import jakarta.validation.constraints.NotNull;

/**
 * @author Paval Shlyk
 * @since 06/11/2023
 */
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface RequestHandlerDefinition {
/**
 * each url pattern should begin from right slash
 */
@NotNull String[] urlPatterns();

@Deprecated
@NotNull RequestMethod method() default RequestMethod.GET;
}
