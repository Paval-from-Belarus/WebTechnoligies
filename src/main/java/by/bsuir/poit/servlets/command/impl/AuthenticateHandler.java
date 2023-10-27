package by.bsuir.poit.servlets.command.impl;

import by.bsuir.poit.bean.User;
import by.bsuir.poit.servlets.command.RequestHandler;
import by.bsuir.poit.utils.AuthorizationUtils;
import by.bsuir.poit.utils.RedirectUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;

import java.util.List;

/**
 * @author Paval Shlyk
 * @since 27/10/2023
 */
public class AuthenticateHandler implements RequestHandler {
public static final int MAX_INACTIVE_INTERVAL = 60 * 10;
public static final String COOKIE_USER_ID = "user_id";
public static final String COOKIE_USER_ROLE = "user_role";

@Override
@SneakyThrows(Exception.class)
public void accept(HttpServletRequest request, HttpServletResponse response) throws Exception {
      User user = (User) request.getAttribute(AuthorizationUtils.USER_ATTRIBUTE);
      assert user != null;
      HttpSession session = request.getSession(false);
      if (session != null) {
	    session.invalidate();
      }
      session = request.getSession(true);
      session.setMaxInactiveInterval(MAX_INACTIVE_INTERVAL);
      List<Cookie> cookies = List.of(
	  new Cookie(COOKIE_USER_ID, String.valueOf(user.getId())),
	  new Cookie(COOKIE_USER_ROLE, String.valueOf(user.getRole()))
      );
      cookies.forEach(response::addCookie);
      if (user.getRole() == User.ADMIN) {
	    response.sendRedirect(RedirectUtils.ADMIN_PAGE);
      }
      if (user.getRole() == User.CLIENT) {
	    response.sendRedirect(RedirectUtils.CLIENT_PAGE);
      }
      response.sendRedirect(RedirectUtils.ERROR_PAGE);
}
}
