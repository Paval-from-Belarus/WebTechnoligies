package by.bsuir.poit.utils;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author Paval Shlyk
 * @since 27/10/2023
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ControllerUtils {
private static final String CONTROLLER_URL = "/jdbc-servlets/api";//by this path gate controller is available
public static final String CLIENT_ENDPOINT = CONTROLLER_URL + "/client";
public static final String ADMIN_ENDPOINT = CONTROLLER_URL + "/admin";
public static final String AUCTION_ENDPOINT = CONTROLLER_URL + "/auction";
private static final String REDIRECT_PAGE = "redirect_page";
private static final Gson PARSER = new Gson();

public static void sendRedirectMessage(HttpServletResponse response, String endpoint) throws IOException {
      PrintWriter writer = response.getWriter();
      Map<String, String> responseMap = Map.of(REDIRECT_PAGE, endpoint);
      writer.write(PARSER.toJson(responseMap));
}
}
