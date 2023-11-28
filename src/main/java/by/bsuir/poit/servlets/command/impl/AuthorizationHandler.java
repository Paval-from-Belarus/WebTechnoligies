package by.bsuir.poit.servlets.command.impl;

import by.bsuir.poit.dto.User;
import by.bsuir.poit.context.RequestHandlerDefinition;
import by.bsuir.poit.servlets.command.RequestHandler;
import by.bsuir.poit.utils.AuthorizationUtils;
import by.bsuir.poit.utils.ControllerUtils;
import by.bsuir.poit.utils.PageUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Paval Shlyk
 * @since 27/10/2023
 */
@RequestHandlerDefinition(urlPatterns = "/auth")
@RequiredArgsConstructor
public class AuthorizationHandler implements RequestHandler {
private static final Logger LOGGER = LogManager.getLogger(AuthorizationHandler.class);

@Override
@SneakyThrows
public void accept(HttpServletRequest request, HttpServletResponse response) throws Exception {
      User user = (User) request.getAttribute(AuthorizationUtils.USER_ATTRIBUTE);
      assert user != null;
      if (user.getRole() == User.ADMIN) {
	    PageUtils.redirectTo(response, ControllerUtils.ADMIN_ENDPOINT);
	    return;
      }
      if (user.getRole() == User.CLIENT) {
	    PageUtils.redirectTo(response, ControllerUtils.CLIENT_ENDPOINT);
	    return;
      }
      response.sendError(HttpServletResponse.SC_FORBIDDEN);
}
}
