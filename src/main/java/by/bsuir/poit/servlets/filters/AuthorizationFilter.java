package by.bsuir.poit.servlets.filters;

import by.bsuir.poit.dto.User;
import by.bsuir.poit.context.Autowired;
import by.bsuir.poit.context.BeanUtils;
import by.bsuir.poit.services.AuthorizationService;
import by.bsuir.poit.utils.AuthorizationUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * This filter accomplishes pre-authorization work.
 * Truly, exactly filter configure Principal and check validness of credentials.
 *
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@WebFilter(filterName = "authenticator")
public class AuthorizationFilter extends HttpFilter {
private static final Logger LOGGER = LogManager.getLogger(AuthorizationFilter.class);
public static final int MAX_INACTIVE_INTERVAL = 60 * 10;
@Autowired
private AuthorizationService authorizationService;

@Override
public void init(FilterConfig filterConfig) {
      BeanUtils.configureFilter(this, filterConfig);
}

@Override
public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
      String login = request.getParameter(AuthorizationUtils.NAME);
      String password = request.getParameter(AuthorizationUtils.PASSWORD);
      try {
	    User user = authorizationService.signIn(login, password);//if method raise exception
	    request.setAttribute(AuthorizationUtils.USER_ATTRIBUTE, user);
	    HttpSession session = request.getSession(false);
	    if (session != null) {
		  session.invalidate();
	    }
	    session = request.getSession(true);
	    session.setMaxInactiveInterval(MAX_INACTIVE_INTERVAL);
	    session.setAttribute(AuthorizationUtils.COOKIE_USER_ID, String.valueOf(user.getId()));
	    session.setAttribute(AuthorizationUtils.COOKIE_USER_ROLE, String.valueOf(user.getRole()));
//	    List<Cookie> cookies = List.of(
//		new Cookie(AuthorizationUtils.COOKIE_USER_ID, String.valueOf(user.getId())),
//		new Cookie(AuthorizationUtils.COOKIE_USER_ROLE, String.valueOf(user.getRole()))
//	    );
//	    for (Cookie cookie : request.getCookies()) {
//		  if (cookie.getName().equals(AuthorizationUtils.COOKIE_USER_ID) ||
//			  cookie.getName().equals(AuthorizationUtils.COOKIE_USER_ROLE)) {
//			cookie.setMaxAge(-1);
//		  }
//	    }
//	    for (Cookie cookie : cookies) {
//		  cookie.setPath(PageUtils.APPLICATION_NAME);
//		  cookie.setMaxAge(MAX_INACTIVE_INTERVAL);
//		  response.addCookie(cookie);
//	    }
//
//	    cookies.forEach(response::addCookie);
	    LOGGER.trace("User with id {} was authorized", user.getId());
      } catch (Exception e) {
	    LOGGER.warn(e);
	    response.sendError(HttpServletResponse.SC_FORBIDDEN);
	    return;
      }
      chain.doFilter(request, response);
}

}
