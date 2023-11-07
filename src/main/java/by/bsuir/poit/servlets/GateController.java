package by.bsuir.poit.servlets;

import by.bsuir.poit.context.Autowired;
import by.bsuir.poit.context.BeanUtils;
import by.bsuir.poit.servlets.command.RequestHandler;
import by.bsuir.poit.servlets.command.RequestHandlerProvider;
import by.bsuir.poit.servlets.command.RequestMethod;
import by.bsuir.poit.utils.RedirectUtils;
import jakarta.servlet.RequestDispatcher;
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
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "Gate", urlPatterns = {"/api/*"})
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
	    String requestUrl = parseRequestUrl(request);
	    RequestHandler handler = handlerProvider.provide(requestUrl, method);
	    if (handler != null) {
		  LOGGER.trace(String.format("Handler %s will process request", handler));
		  handler.accept(request, response);
	    } else {
		  LOGGER.warn("No request handler for request mapping {}", requestUrl);
		  RequestDispatcher dispatcher = request.getRequestDispatcher(RedirectUtils.ERROR_PAGE);
		  dispatcher.forward(request, response);
	    }
      } catch (Throwable e) {
	    LOGGER.error("Gate controller catch exception with message ={} \n {}", e.getMessage(), Arrays.toString(e.getStackTrace()));
	    response.sendError(HttpServletResponse.SC_FORBIDDEN);
      }
}

private final Pattern REQUEST_PATTERN = Pattern.compile(".*/api/(.+)");

private String parseRequestUrl(HttpServletRequest request) {
      Matcher matcher = REQUEST_PATTERN.matcher(request.getRequestURI());
      String requestUrl;
      if (matcher.find()) {
	    requestUrl = matcher.group(1);
	    if (!requestUrl.startsWith("/")) {
		  requestUrl = "/" + requestUrl;
	    }
      } else {
	    throw new IllegalStateException("Impossible to access page");
      }
      return requestUrl;
}

@Override
public void init(ServletConfig config) throws ServletException {
      ServletContext context = config.getServletContext();
      BeanUtils.configureServlet(this, config);
}
}
