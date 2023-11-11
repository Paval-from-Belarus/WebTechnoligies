package by.bsuir.poit.servlets.filters;

import by.bsuir.poit.bean.User;
import by.bsuir.poit.context.Autowired;
import by.bsuir.poit.context.BeanUtils;
import by.bsuir.poit.services.AuthorizationService;
import by.bsuir.poit.servlets.UserDetails;
import by.bsuir.poit.utils.PageUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
private final List<String> UNAUTHORIZED_ACCESS_PAGES = List.of(
    "/lobby", "/api/reg", "/api/auth", "/error"
);
private final Pattern START_PAGE_PATTERN = Pattern.compile("/jdbc-servlets/(index\\.(htm|html|jsp))?$");
private final Pattern USER_PAGE_PATTERN = Pattern.compile("/jdbc-servlets/user(\\.(htm|html|jsp))?$");

@Override
protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
      Principal principal = request.getUserPrincipal();
      String requestURI = request.getRequestURI();
      if (isRequestToStartPage(requestURI)) {
	    PageUtils.redirectTo(response, PageUtils.START_PAGE);
	    return;
      }
      if (isRequestToUserPage(requestURI)) {
	    PageUtils.redirectTo(response, pageByUserRole(principal));
      }
      if (principal != null) {
	    chain.doFilter(request, response);
	    return;
      }
      if (UNAUTHORIZED_ACCESS_PAGES.stream().noneMatch(requestURI::contains)) {
	    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	    return;
      }
      chain.doFilter(request, response);
}

private boolean isRequestToStartPage(String requestUri) {
      return START_PAGE_PATTERN.matcher(requestUri).find();
}

private boolean isRequestToUserPage(String requestUri) {
      return START_PAGE_PATTERN.matcher(requestUri).find();
}

private String pageByUserRole(Principal principal) {
      UserDetails details = (UserDetails) principal;
      if (details.id() == User.CLIENT) {
	    return PageUtils.CLIENT_PAGE;
      }
      if (details.id() == User.ADMIN) {
	    return PageUtils.ADMIN_PAGE;
      }
      return PageUtils.ERROR_PAGE;
}
}
