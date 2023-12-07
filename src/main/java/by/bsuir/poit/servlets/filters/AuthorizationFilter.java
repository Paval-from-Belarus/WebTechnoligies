package by.bsuir.poit.servlets.filters;

import by.bsuir.poit.context.BeanUtils;
import by.bsuir.poit.dto.UserDto;
import by.bsuir.poit.services.AuthorizationService;
import by.bsuir.poit.utils.AuthorizationUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * This filter accomplishes pre-authorization work.
 * Truly, exactly filter configure Principal and check validness of credentials.
 *
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@Component
@WebFilter(filterName = "authenticator")
public class AuthorizationFilter extends HttpFilter {
private static final Logger LOGGER = LogManager.getLogger(AuthorizationFilter.class);
public static final int MAX_INACTIVE_INTERVAL = 60 * 10;
@Autowired
private AuthorizationService authorizationService;

@Override
public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
      String login = request.getParameter(AuthorizationUtils.NAME);
      String password = request.getParameter(AuthorizationUtils.PASSWORD);
      try {
	    UserDto user = authorizationService.signIn(login, password);//if method raise exception
	    request.setAttribute(AuthorizationUtils.USER_ATTRIBUTE, user);
	    HttpSession session = request.getSession(false);
	    if (session != null) {
		  session.invalidate();
	    }
	    session = request.getSession(true);
	    session.setMaxInactiveInterval(MAX_INACTIVE_INTERVAL);
	    session.setAttribute(AuthorizationUtils.COOKIE_USER_ID, String.valueOf(user.getId()));
	    session.setAttribute(AuthorizationUtils.COOKIE_USER_ROLE, String.valueOf(user.getRole()));
	    LOGGER.trace("User with id {} was authorized", user.getId());
      } catch (Exception e) {
	    LOGGER.warn(e);
	    response.sendError(HttpServletResponse.SC_FORBIDDEN);
	    return;
      }
      chain.doFilter(request, response);
}

}
