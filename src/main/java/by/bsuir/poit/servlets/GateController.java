package by.bsuir.poit.servlets;

import by.bsuir.poit.context.Autowired;
import by.bsuir.poit.context.BeanUtils;
import by.bsuir.poit.servlets.command.RequestHandler;
import by.bsuir.poit.servlets.command.RequestHandlerProvider;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
      processRequest(req, resp);
}

@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      processRequest(req, resp);
}
private void processRequest(HttpServletRequest request, HttpServletResponse response) {
      try {
            RequestHandler handler = handlerProvider.provide(request.getRequestURI());
            LOGGER.info(String.format("Handler %s will process request", handler));
            handler.accept(request, response);
      } catch (Exception e) {

            LOGGER.error(e);
      }
}

@Override
public void init(ServletConfig config) throws ServletException {
      ServletContext context = config.getServletContext();
      BeanUtils.configureServlet(this, config);
}
}
