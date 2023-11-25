package by.bsuir.poit.servlets.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The context automatically search each bean annotated with {@link by.bsuir.poit.context.RequestHandlerDefinition}
 * and supposed to be injected into {@link by.bsuir.poit.context.RequestHandlerMap}
 *
 * @author Paval Shlyk
 * @since 27/10/2023
 */
@FunctionalInterface
public interface RequestHandler {

void accept(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
