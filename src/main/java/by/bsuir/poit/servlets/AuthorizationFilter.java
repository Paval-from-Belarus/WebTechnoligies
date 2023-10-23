package by.bsuir.poit.servlets;

import by.bsuir.poit.dao.UserDao;
import by.bsuir.poit.dao.entities.User;
import by.bsuir.poit.utils.AuthorizationUtils;
import by.bsuir.poit.utils.ControllerUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@WebFilter(filterName = "authenticator", urlPatterns = {"/auth"})
public class AuthorizationFilter implements Filter {
public static final String PASSWORD = "password";
public static final String NAME = "user_name";
private static Logger LOGGER = LogManager.getLogger(AuthorizationFilter.class);
private UserDao userDao;
@Override
public void init(FilterConfig filterConfig) {
      ServletContext context = filterConfig.getServletContext();
      userDao = (UserDao) context.getAttribute(UserDao.class.getName());
      assert userDao != null;
}

@Override
public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      String name = request.getParameter(NAME);
      String password = request.getParameter(PASSWORD);
      if (name == null || password == null || !hasValidCredentials(name, password)) {
            final String msg = String.format("Attempt to authenticate by invalid credentials. Name=%s. Password=%s", name, password);
            LOGGER.warn(msg);
            RequestDispatcher dispatcher = request.getRequestDispatcher(ControllerUtils.ERROR_PAGE);
            dispatcher.forward(request, response);
      }
      chain.doFilter(request, response);
}


private boolean hasValidCredentials(@NotNull String name, @NotNull String password) {
      Optional<User> optionalUser = userDao.findByUserName(name);
      if (optionalUser.isEmpty()){
            return false;
      }
      User user = optionalUser.get();
      String salt = user.getSecuritySalt();
      String passwordHash = AuthorizationUtils.encodeToken(password, salt);
      return user.getPasswordHash().equals(passwordHash);
}

}
