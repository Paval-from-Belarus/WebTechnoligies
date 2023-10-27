package by.bsuir.poit.servlets;

import by.bsuir.poit.services.AuthorizationService;
import by.bsuir.poit.utils.AuthorizationUtils;
import by.bsuir.poit.utils.ControllerUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.validation.constraints.NotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@WebFilter(filterName = "authenticator", urlPatterns = {"/auth"})
public class AuthorizationFilter implements Filter {
private static final Logger LOGGER = LogManager.getLogger(AuthorizationFilter.class);
private AuthorizationService authorizationService;

@Override
public void init(FilterConfig filterConfig) {
      ServletContext context = filterConfig.getServletContext();
      authorizationService = (AuthorizationService) context.getAttribute(AuthorizationService.class.getName());
      assert authorizationService != null;
}

@Override
public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      String login = request.getParameter(AuthorizationUtils.NAME);
      String password = request.getParameter(AuthorizationUtils.PASSWORD);
      authorizationService.signIn(login, password);//if method raise exception
      chain.doFilter(request, response);
}

}
