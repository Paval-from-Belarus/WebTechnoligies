package by.bsuir.poit.servlets.filters;

import by.bsuir.poit.utils.RedirectUtils;
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
private final Pattern START_PAGE_PATTERN = Pattern.compile("/jdbc-servlets/(index\\.(html|htm|jsp))?$");

@Override
protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
      Principal principal = req.getUserPrincipal();
      if (principal != null) {
	    chain.doFilter(req, res);
	    return;
      }
      String requestURI = req.getRequestURI();
      if (isRequestToStartPage(requestURI)) {
	    RedirectUtils.redirectTo(res, RedirectUtils.START_PAGE);
	    return;
      }
      if (UNAUTHORIZED_ACCESS_PAGES.stream().noneMatch(requestURI::contains)) {
	    res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	    return;
      }
      chain.doFilter(req, res);
}

private boolean isRequestToStartPage(String requestUri) {
      return START_PAGE_PATTERN.matcher(requestUri).find();
}

}
