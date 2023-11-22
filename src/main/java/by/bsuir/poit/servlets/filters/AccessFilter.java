package by.bsuir.poit.servlets.filters;

import by.bsuir.poit.bean.User;
import by.bsuir.poit.context.Autowired;
import by.bsuir.poit.context.BeanUtils;
import by.bsuir.poit.services.AuthorizationService;
import by.bsuir.poit.servlets.UserDetails;
import by.bsuir.poit.utils.ControllerUtils;
import by.bsuir.poit.utils.PageUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Paval Shlyk
 * @since 07/11/2023
 */
@WebFilter(filterName = "access")
public class AccessFilter extends HttpFilter {
private static final Logger LOGGER = LogManager.getLogger(AccessFilter.class);
private final List<String> UNAUTHORIZED_ACCESS_PAGES = List.of(
    "/lobby", "/api/reg", "/api/auth", "/error", "/js", "/css"
);
private final Pattern START_PAGE_PATTERN = Pattern.compile("/jdbc-servlets/(index\\.(htm|html|jsp))?$");
private final Pattern USER_PAGE_PATTERN = Pattern.compile("/jdbc-servlets/user(\\.(htm|html|jsp))?$");

@Override
protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
      Principal principal = request.getUserPrincipal();
      String requestURI = request.getRequestURI();
	LOGGER.trace("Incoming request URI: {}", requestURI);
      if (isRequestToStartPage(requestURI)) {
	    PageUtils.redirectTo(response, PageUtils.START_PAGE);
	    LOGGER.trace("Redirection to start page");
	    return;
      }
      if (principal != null && isRequestToUserPage(requestURI)) {
	    LOGGER.trace("Redirection to corresponding user page");
	    PageUtils.redirectTo(response, pageByUserRole(principal));
	    return;
      }
      if (principal != null) {
	    LOGGER.trace("Authorized principal request");
	    chain.doFilter(request, response);
	    return;
      }
      if (UNAUTHORIZED_ACCESS_PAGES.stream().noneMatch(requestURI::contains)) {
	    LOGGER.trace("Unauthorized request");
	    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	    return;
      }
      LOGGER.trace("Resource request");
      chain.doFilter(request, response);
}

private boolean isRequestToStartPage(String requestUri) {
      return START_PAGE_PATTERN.matcher(requestUri).find();
}

private boolean isRequestToUserPage(String requestUri) {
      return USER_PAGE_PATTERN.matcher(requestUri).find();
}

private String pageByUserRole(Principal principal) {
      UserDetails details = (UserDetails) principal;
      if (details.id() == User.CLIENT) {
	    return ControllerUtils.CLIENT_ENDPOINT;
      }
      if (details.id() == User.ADMIN) {
	    return ControllerUtils.ADMIN_ENDPOINT;
      }
      return PageUtils.ERROR_PAGE;
}
}
