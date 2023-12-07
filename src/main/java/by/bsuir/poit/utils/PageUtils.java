package by.bsuir.poit.utils;

import by.bsuir.poit.model.Lot;
import by.bsuir.poit.model.User;
import by.bsuir.poit.servlets.UserPageType;
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
 * This is a utility class that provides various helper methods and constants for handling pages in a web application.
 *
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
public static final String USER_PAGE = "/user";
public static final String AUCTION_ASSIGNMENT_PAGE = "/assignment";
public static final String AUCTION = "/auction";
//should be injected
public static final String APPLICATION_NAME = "";
public static final String ERROR_REASON = "errorReasons";
public static final String ERROR_CODE = "errorCode";
public static final String LOT_STATUSES = "lotStatuses";
public static final Map<Short, String> LOT_STATUSES_MAP = Map.of(
    Lot.BLOCKED_STATUS, "Blocked",
    Lot.BEFORE_AUCTION_STATUS, "Ready to auction",
    Lot.SELL_STATUS, "Customer is found",
    Lot.AUCTION_STATUS, "The lot is placed at auction",
    Lot.SENT_STATUS, "The lot is sent to customer",
    Lot.DELIVERIED_STATUS, "The lot is deliveried to customer"
);

/**
 * Sends an error response to the client with the specified error code and messages.
 *
 * @param request   the HttpServletRequest object
 * @param response  the HttpServletResponse object
 * @param errorCode the error code
 * @param messages  the error messages
 */
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

/**
 * Determines the {@link UserPageType} based on the given role.
 *
 * @param role the user role
 * @return the UserPageType corresponding to the role
 * @throws IllegalStateException if an invalid role is passed
 */
public static UserPageType typeOfRole(short role) {
      if (role == User.ADMIN) {
	    return UserPageType.ADMIN;
      }
      if (role == User.CLIENT) {
	    return UserPageType.CLIENT;
      }
      throw new IllegalStateException("The invalid role was passed to build UserPageType");
}

/**
 * Redirects the client to the specified servlet page.
 *
 * @param response    the HttpServletResponse object
 * @param servletPage the servlet page URL
 * @throws IOException if an I/O error occurs
 */
public static void redirectTo(HttpServletResponse response, String servletPage) throws IOException {
      response.sendRedirect(APPLICATION_NAME + servletPage);
}

/**
 * Forwards the request and response objects to the specified servlet page.
 *
 * @param request     the HttpServletRequest object
 * @param response    the HttpServletResponse object
 * @param servletPage the servlet page URL
 * @throws IOException      if an I/O error occurs
 * @throws ServletException if a servlet error occurs
 */
public static void forwardTo(HttpServletRequest request, HttpServletResponse response, String servletPage) throws IOException, ServletException {
      RequestDispatcher dispatcher = request.getRequestDispatcher(servletPage);
      dispatcher.forward(request, response);
}

/**
 * Includes the specified servlet page in the response.
 *
 * @param request     the HttpServletRequest object
 * @param response    the HttpServletResponse object
 * @param servletPage the servlet page URL
 * @throws ServletException if a servlet error occurs
 * @throws IOException      if an I/O error occurs
 */
public static void includeWith(HttpServletRequest request, HttpServletResponse response, String servletPage) throws ServletException, IOException {
      RequestDispatcher dispatcher = request.getRequestDispatcher(servletPage);
      dispatcher.include(request, response);
}

}
