package by.bsuir.poit.servlets.command.impl;

import by.bsuir.poit.context.RequestHandlerDefinition;
import by.bsuir.poit.servlets.command.RequestHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Paval Shlyk
 * @since 08/11/2023
 */
@RequestHandlerDefinition(urlPatterns = "/admin")
public class AdminHandler implements RequestHandler {

@Override
public void accept(HttpServletRequest request, HttpServletResponse response) throws Exception {

}
}
