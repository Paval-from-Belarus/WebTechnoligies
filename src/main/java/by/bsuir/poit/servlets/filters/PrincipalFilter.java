package by.bsuir.poit.servlets.filters;

import by.bsuir.poit.servlets.UserDetails;
import by.bsuir.poit.servlets.UserPrincipalHttpServletRequest;
import by.bsuir.poit.servlets.exception.DuplicatedCookieException;
import by.bsuir.poit.utils.AuthorizationUtils;
import by.bsuir.poit.utils.PageUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Build principal by session attributes supplied from {@link AuthorizationFilter}
 *
 * @author Paval Shlyk
 * @since 07/11/2023
 */
@WebFilter(filterName = "principal-builder")
public class PrincipalFilter extends HttpFilter {
private static final Logger LOGGER = LogManager.getLogger(PrincipalFilter.class);

@Override
protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
      HttpSession session = request.getSession(false);
      if (session == null) {
	    chain.doFilter(request, response);
	    return;
      }
      try {
	    Optional<Principal> principal = buildPrincipalByCookies(request, response);
	    if (principal.isPresent()) {
		  chain.doFilter(new UserPrincipalHttpServletRequest(request, principal.get()), response);
	    } else {
		  chain.doFilter(request, response);
	    }
      } catch (DuplicatedCookieException e) {
	    PageUtils.redirectTo(response, PageUtils.ERROR_PAGE);
      }
}

private Optional<Principal> buildPrincipalByCookies(HttpServletRequest request, HttpServletResponse response) throws IllegalStateException {
      HttpSession session = request.getSession(false);
      if (session == null) {
	    return Optional.empty();
      }
      String userRole;
      String userId;
      try {
	    userRole = (String) session.getAttribute(AuthorizationUtils.COOKIE_USER_ROLE);
	    userId = (String) session.getAttribute(AuthorizationUtils.COOKIE_USER_ID);
	    if (userRole == null || userId == null) {
		  LOGGER.trace("No principal was found for request");
		  return Optional.empty();
	    }
      } catch (Exception e) {
	    LOGGER.warn(e);
	    return Optional.empty();
      }

//      StringBuilder strCookies = new StringBuilder();
//      for (Cookie cookie : request.getCookies()) {
//	    strCookies.append("Name=").append(cookie.getName()).append(",")
//		.append("Value=").append(cookie.getValue()).append("\n");
//      }
//      LOGGER.trace("The request holds following cookies: {};", strCookies);
//      for (Cookie cookie : request.getCookies()) {
//	    if (cookies.containsKey(cookie.getName())) {
//		  LOGGER.warn("Client side holds duplicated cookies. They will be deleted");
//		  AuthorizationUtils.removePrincipalCookies(request.getCookies(), response);
//		  throw new DuplicatedCookieException("Duplicated cookies on server side");
//	    }
//	    if (AuthorizationUtils.isPrincipalCookie(cookie)) {
//		  cookies.put(cookie.getName(), cookie.getValue());
//	    }
//      }
//      if (cookies.size() != AuthorizationUtils.PRINCIPAL_COOKIE_COUNT) {

//	    return Optional.empty();
//      }
      Optional<Principal> principal = Optional.empty();
      try {
	    UserDetails userDetails = UserDetails.builder()
					  .role(Short.parseShort(userRole))
					  .id(Long.parseLong(userId))
					  .build();
	    LOGGER.trace("Principal {} is built", userDetails);
	    principal = Optional.of(userDetails);
      } catch (Exception e) {
	    LOGGER.warn("Failed to parse UserDetails from Cookie: {}", e.toString());
      }
      return principal;
}


}
