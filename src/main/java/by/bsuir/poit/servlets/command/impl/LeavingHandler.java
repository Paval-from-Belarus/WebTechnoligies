package by.bsuir.poit.servlets.command.impl;

import by.bsuir.poit.context.RequestHandlerDefinition;
import by.bsuir.poit.services.AuthorizationService;
import by.bsuir.poit.services.exception.resources.ResourceBusyException;
import by.bsuir.poit.servlets.UserDetails;
import by.bsuir.poit.servlets.command.RequestHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.Principal;

/**
 * @author Paval Shlyk
 * @since 07/11/2023
 */
@RequestHandlerDefinition(urlPatterns = "/leave")
@RequiredArgsConstructor
public class LeavingHandler implements RequestHandler {
private static final Logger LOGGER = LogManager.getLogger(LeavingHandler.class);
private final AuthorizationService authorizationService;

@Override
public void accept(HttpServletRequest request, HttpServletResponse response) throws Exception {
      UserDetails details = (UserDetails) request.getUserPrincipal();
      try {
	    authorizationService.signOut(details.id());
      } catch (ResourceBusyException e) {
	    LOGGER.warn(e);
	    response.sendError(HttpServletResponse.SC_FORBIDDEN);
      }
}
}
