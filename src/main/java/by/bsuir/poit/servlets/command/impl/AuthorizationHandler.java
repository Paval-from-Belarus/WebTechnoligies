package by.bsuir.poit.servlets.command.impl;

import by.bsuir.poit.bean.User;
import by.bsuir.poit.context.RequestHandlerDefinition;
import by.bsuir.poit.servlets.command.RequestHandler;
import by.bsuir.poit.utils.AuthorizationUtils;
import by.bsuir.poit.utils.RedirectUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * @author Paval Shlyk
 * @since 27/10/2023
 */
@RequestHandlerDefinition(urlPatterns = "/auth")
@RequiredArgsConstructor
public class AuthorizationHandler implements RequestHandler {
private static final Logger LOGGER = LogManager.getLogger(AuthorizationHandler.class);
public static final int MAX_INACTIVE_INTERVAL = 60 * 10;


@Override
@SneakyThrows
public void accept(HttpServletRequest request, HttpServletResponse response) throws Exception {
      User user = (User) request.getAttribute(AuthorizationUtils.USER_ATTRIBUTE);
      assert user != null;
      HttpSession session = request.getSession(false);
      if (session != null) {
	    session.invalidate();
      }
      session = request.getSession(true);
      session.setMaxInactiveInterval(MAX_INACTIVE_INTERVAL);
      session.setAttribute(AuthorizationUtils.COOKIE_USER_ID, String.valueOf(user.getId()));
      session.setAttribute(AuthorizationUtils.COOKIE_USER_ROLE, String.valueOf(user.getRole()));
      session.setAttribute("test_attr", (short) 12);
      LOGGER.trace("User with id {} was authorized", user.getId());
      if (user.getRole() == User.ADMIN) {
	    RedirectUtils.sendRedirectMessage(response, RedirectUtils.ADMIN_PAGE);
	    return;
      }
      if (user.getRole() == User.CLIENT) {
	    RedirectUtils.sendRedirectMessage(response, RedirectUtils.CLIENT_PAGE);
	    return;
      }
      response.sendError(HttpServletResponse.SC_FORBIDDEN);
}
}
