package by.bsuir.poit.servlets.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 21/11/2023
 */
@WebFilter(filterName = "params-resolver")
public class CommonParametersResolverFilter extends HttpFilter {
public static final String LANGUAGE = "lang";
public static final String NEXT_LANGUAGE = "nextLang";
public static final String DEFAULT_LANGUAGE = "en";
public static final String ALTERNATIVE_LANGUAGE = "by";
public static final List<String> AVAILABLE_LANGUAGES = List.of(
    DEFAULT_LANGUAGE, ALTERNATIVE_LANGUAGE
);
private final Map<String, String> nextLanguageMap = Map.of(
    DEFAULT_LANGUAGE, ALTERNATIVE_LANGUAGE,
    ALTERNATIVE_LANGUAGE, DEFAULT_LANGUAGE
);

@Override
protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
      String langParameter = request.getParameter(LANGUAGE);
      HttpSession session = request.getSession();
      if (session == null) {
	    chain.doFilter(request, response);
	    return;
      }
      if (langParameter == null && session.getAttribute(LANGUAGE) == null) {
	    session.setAttribute(LANGUAGE, DEFAULT_LANGUAGE);
	    request.setAttribute(LANGUAGE, DEFAULT_LANGUAGE);
	    request.setAttribute(NEXT_LANGUAGE, nextLanguageMap.get(DEFAULT_LANGUAGE));
	    chain.doFilter(request, response);
	    return;
      }
      if (langParameter != null && AVAILABLE_LANGUAGES.stream().noneMatch(langParameter::equals)) {
	    response.sendError(HttpServletResponse.SC_NOT_FOUND, "No localization for given parameter");
	    return;
      }
      if (langParameter != null) {
	    session.setAttribute(LANGUAGE, langParameter);
      } else {
	    langParameter = (String) session.getAttribute(LANGUAGE);
      }
      request.setAttribute(LANGUAGE, langParameter);
      request.setAttribute(NEXT_LANGUAGE, nextLanguageMap.get(langParameter));
      chain.doFilter(request, response);
}
}
