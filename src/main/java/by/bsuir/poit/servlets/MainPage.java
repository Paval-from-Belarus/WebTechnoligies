package by.bsuir.poit.servlets;

import by.bsuir.poit.dao.impl.UserDaoImpl;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author Paval Shlyk
 * @since 15/09/2023
 */
public class MainPage extends HttpServlet {
private UserDaoImpl userDao;

@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      if (userDao != null) {
	    response.getWriter().write("No null");
      }
      response.getWriter().write("Hello World!");
}

@Override
public void init(ServletConfig config) throws ServletException {
      ServletContext context = config.getServletContext();
      this.userDao = (UserDaoImpl) context.getAttribute(UserDaoImpl.class.getName());
      super.init(config);
}
}
