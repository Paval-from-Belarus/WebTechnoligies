package by.bsuir.poit.servlets.filters;

import by.bsuir.poit.bean.User;
import by.bsuir.poit.context.Autowired;
import by.bsuir.poit.context.BeanUtils;
import by.bsuir.poit.services.AuthorizationService;
import by.bsuir.poit.utils.AuthorizationUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
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
@Autowired
private AuthorizationService authorizationService;

@Override
public void init(FilterConfig filterConfig) {
      BeanUtils.configureFilter(this, filterConfig);
}

@Override
public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      String login = request.getParameter(AuthorizationUtils.NAME);
      String password = request.getParameter(AuthorizationUtils.PASSWORD);
      User user = authorizationService.signIn(login, password);//if method raise exception
      request.setAttribute(AuthorizationUtils.USER_ATTRIBUTE, user);
      chain.doFilter(request, response);
}

}
