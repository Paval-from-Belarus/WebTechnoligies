package by.bsuir.poit.servlets.command.impl;

import by.bsuir.poit.context.RequestHandlerDefinition;
import by.bsuir.poit.services.exception.resources.ResourceModifyingException;
import by.bsuir.poit.servlets.command.RequestHandler;
import by.bsuir.poit.servlets.command.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Paval Shlyk
 * @since 27/10/2023
 */
@RequestHandlerDefinition(urlPatterns = "/auth", method = RequestMethod.GET)
public class AuctionHandler implements RequestHandler {
@Override
public void accept(HttpServletRequest request, HttpServletResponse response) throws Exception {
      throw new ResourceModifyingException("Slow exception");
}
}
