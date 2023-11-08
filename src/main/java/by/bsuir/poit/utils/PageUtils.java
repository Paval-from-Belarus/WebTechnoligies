package by.bsuir.poit.utils;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PageUtils {
public static final String START_PAGE = "/lobby";
public static final String ERROR_PAGE = "/error";
public static final String REGISTRATION_PAGE = "/reg";
public static final String AUTHORIZATION_PAGE = "/auth";
public static final String ADMIN_PAGE = "/admin";
public static final String CLIENT_PAGE = "/client";
//should be injected
public static final String APPLICATION_NAME = "/jdbc-servlets";
public static final String ERROR_REASON = "errorReasons";
public static final String ERROR_CODE = "errorCode";
public static void sendError(HttpServletRequest request, HttpServletResponse response, int errorCode, String... messages) {
      final String REASON_LBL = "reason_";
      Map<String, String> map = new HashMap<>();
      int index = 0;
      for (String message : messages) {
	    map.put(REASON_LBL + index, message);
	    index += 1;
      }
      request.setAttribute(ERROR_REASON, map);
      request.setAttribute(ERROR_CODE, errorCode);
      try {
	    forwardTo(request, response, ERROR_PAGE);
      } catch (ServletException | IOException e) {
            throw new IllegalStateException(e);
      }

}

public static void redirectTo(HttpServletResponse response, String servletPage) throws IOException {
      response.sendRedirect(APPLICATION_NAME + servletPage);
}

public static void forwardTo(HttpServletRequest request, HttpServletResponse response, String servletPage) throws IOException, ServletException {
      RequestDispatcher dispatcher = request.getRequestDispatcher(servletPage);
      dispatcher.forward(request, response);
}

public static void includeWith(HttpServletRequest request, HttpServletResponse response, String servletPage) throws ServletException, IOException {
      RequestDispatcher dispatcher = request.getRequestDispatcher(servletPage);
      dispatcher.include(request, response);
}


}
