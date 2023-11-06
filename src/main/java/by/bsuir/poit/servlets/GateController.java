package by.bsuir.poit.servlets;

import by.bsuir.poit.context.Autowired;
import by.bsuir.poit.context.BeanUtils;
import by.bsuir.poit.servlets.command.RequestHandler;
import by.bsuir.poit.servlets.command.RequestHandlerProvider;
import by.bsuir.poit.servlets.command.RequestMethod;
import by.bsuir.poit.utils.RedirectUtils;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "Gate", urlPatterns = {"/*"})
public class GateController extends HttpServlet {
private static final Logger LOGGER = LogManager.getLogger(GateController.class);
@Autowired
private RequestHandlerProvider handlerProvider;

@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      processRequest(req, resp, RequestMethod.GET);
}

@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      processRequest(req, resp, RequestMethod.POST);
}

@SneakyThrows
private void processRequest(HttpServletRequest request, HttpServletResponse response, RequestMethod method) {
      try {
	    RequestHandler handler = handlerProvider.provide(request.getRequestURI(), method);
	    if (handler != null) {
		  LOGGER.info(String.format("Handler %s will process request", handler));
		  handler.accept(request, response);
	    } else {
		  LOGGER.warn("No request handler for request mapping {}", request.getRequestURI());
	    }
      } catch (Exception e) {
	    LOGGER.error(e);
	    response.sendRedirect(RedirectUtils.ERROR_PAGE);
      }
}

@Override
public void init(ServletConfig config) throws ServletException {
      ServletContext context = config.getServletContext();
      BeanUtils.configureServlet(this, config);
}
}
