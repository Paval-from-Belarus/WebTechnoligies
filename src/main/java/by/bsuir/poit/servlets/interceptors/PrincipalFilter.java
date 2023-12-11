package by.bsuir.poit.servlets.interceptors;

import by.bsuir.poit.servlets.UserDetails;
import by.bsuir.poit.servlets.UserPrincipalHttpServletRequest;
import by.bsuir.poit.servlets.exception.DuplicatedCookieException;
import by.bsuir.poit.utils.AuthorizationUtils;
import by.bsuir.poit.utils.PageUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

/**
 * Build principal by session attributes supplied from {@link AuthorizationInterceptor}
 *
 * @author Paval Shlyk
 * @since 07/11/2023
 */
@Component
public class PrincipalFilter extends OncePerRequestFilter {
private static final Logger LOGGER = LogManager.getLogger(PrincipalFilter.class);

@Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
      HttpSession session = request.getSession(false);
      if (session == null) {
	    chain.doFilter(request, response);
      }
      try {
	    Optional<Principal> principal = buildPrincipalByCookies(request, response);
	    if (principal.isPresent()) {
		  chain.doFilter(new UserPrincipalHttpServletRequest(request, principal.get()), response);
	    } else {
		  chain.doFilter(request, response);
	    }
	    return;
      } catch (DuplicatedCookieException e) {
	    PageUtils.redirectTo(response, PageUtils.ERROR_PAGE);
      }
      chain.doFilter(request, response);
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
