package by.bsuir.poit.servlets.filters;

import by.bsuir.poit.servlets.UserDetails;
import by.bsuir.poit.servlets.UserPrincipalHttpServletRequest;
import by.bsuir.poit.utils.AuthorizationUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.IOException;
import java.security.Principal;
import java.util.UUID;

/**
 * @author Paval Shlyk
 * @since 07/11/2023
 */
@WebFilter(filterName = "principal-builder")
public class PrincipalFilter extends HttpFilter {
private static final Logger LOGGER = LogManager.getLogger(PrincipalFilter.class);

@Override
protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
      UserDetails userDetails = null;
      HttpSession session = req.getSession(false);
      try {
	    if (session != null) {
		  userDetails = UserDetails.builder()
				    .role(Short.parseShort((String) session.getAttribute(AuthorizationUtils.COOKIE_USER_ROLE)))
				    .id(Long.parseLong((String) session.getAttribute(AuthorizationUtils.COOKIE_USER_ID)))
				    .build();
		  if (session.getAttribute("test_attr") != null) {
			LOGGER.trace("class for principal field {}", session.getAttribute("test_attr").getClass());
		  }
	    }
	    if (userDetails != null) {
		  LOGGER.trace("Principal {} is built", userDetails);
		  chain.doFilter(new UserPrincipalHttpServletRequest(req, userDetails), res);
	    } else {
		  LOGGER.trace("No principal was found for request");
		  chain.doFilter(req, res);
	    }

      } catch (Exception e) {
	    LOGGER.warn("Failed to parse UserDetails from Cookie: {}", e.toString());
      }
}
}
