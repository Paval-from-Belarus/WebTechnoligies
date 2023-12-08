package by.bsuir.poit.servlets.interceptors;

import by.bsuir.poit.model.User;
import by.bsuir.poit.servlets.UserDetails;
import by.bsuir.poit.utils.ControllerUtils;
import by.bsuir.poit.utils.PageUtils;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.security.Principal;
import java.util.List;
import java.util.regex.Pattern;

/**
 * This filter verifies access to application endpoints (such kind of WebSecurityConfig).
 * There is a list of available endpoints for unauthorized access (generally, registration/authorization endpoints; resource endpoints)
 *
 * @author Paval Shlyk
 * @since 07/11/2023
 */
@Component
@RequiredArgsConstructor
public class AccessInterceptor implements HandlerInterceptor {
private static final Logger LOGGER = LogManager.getLogger(AccessInterceptor.class);
private final List<String> UNAUTHORIZED_ACCESS_PAGES = List.of(
    "/lobby", "/api/reg", "/api/auth", "/error", "/js", "/css"
);
private final Pattern START_PAGE_PATTERN = Pattern.compile("/(index\\.(htm|html|jsp))?$");
private final Pattern USER_PAGE_PATTERN = Pattern.compile("/user(\\.(htm|html|jsp))?$");

@Override
public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
      Principal principal = request.getUserPrincipal();
      String requestURI = request.getRequestURI();
      LOGGER.trace("Incoming request URI: {}", requestURI);
      if (isRequestToStartPage(requestURI)) {
	    PageUtils.redirectTo(response, PageUtils.START_PAGE);
	    LOGGER.trace("Redirection to start page");
	    return false;
      }
      if (principal != null && isRequestToUserPage(requestURI)) {
	    LOGGER.trace("Redirection to corresponding user page");
	    PageUtils.redirectTo(response, pageByUserRole(principal));
	    return false;
      }
      if (principal != null) {
	    LOGGER.trace("Authorized principal request");
	    return true;
      }
      if (UNAUTHORIZED_ACCESS_PAGES.stream().noneMatch(requestURI::contains)) {
	    LOGGER.trace("Unauthorized request");
	    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	    return false;
      }
      LOGGER.trace("Resource request");
      return true;
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
