package by.bsuir.poit.utils;

import com.google.gson.Gson;
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
public final class RedirectUtils {
public static final String START_PAGE = "/lobby";
public static final String ERROR_PAGE = "/error";
public static final String REGISTRATION_PAGE = "/reg";
public static final String AUTHORIZATION_PAGE = "/auth";
public static final String ADMIN_PAGE = "/admin";
public static final String CLIENT_PAGE = "/client";
private static final String APPLICATION_NAME = "/jdbc-servlets";
public static final String REDIRECT_PAGE = "redirect_page";

public static void redirectTo(HttpServletResponse response, String servletPage) throws IOException {
      response.sendRedirect(APPLICATION_NAME + servletPage);
}

private static final Gson PARSER = new Gson();
public static void sendRedirectMessage(HttpServletResponse response, String servletPage) throws IOException{
      PrintWriter writer = response.getWriter();
      Map<String, String> responseMap = Map.of(REDIRECT_PAGE, APPLICATION_NAME + servletPage);
      writer.write(PARSER.toJson(responseMap));
}

}
