package by.bsuir.poit.utils;

import com.google.gson.Gson;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.PrintWriter;
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

public static void redirectTo(HttpServletResponse response, String servletPage) throws IOException {
      response.sendRedirect(APPLICATION_NAME + servletPage);
}

public static void forwardTo(HttpServletRequest request, HttpServletResponse response, String servletPage) throws IOException, ServletException {
      RequestDispatcher dispatcher = request.getRequestDispatcher(APPLICATION_NAME + servletPage);
      dispatcher.forward(request, response);
}



}
