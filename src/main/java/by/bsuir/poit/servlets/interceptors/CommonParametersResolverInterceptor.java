package by.bsuir.poit.servlets.interceptors;

import jakarta.servlet.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;
import java.util.Map;

/**
 * The interceptor resolve common for each page attribute (such as language)
 *
 * @author Paval Shlyk
 * @since 21/11/2023
 */
@Component
public class CommonParametersResolverInterceptor implements HandlerInterceptor {
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
public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
      String langParameter = request.getParameter(LANGUAGE);
      HttpSession session = request.getSession();
      if (session == null) {
	    return true;//do nothing
      }
      if (langParameter == null && session.getAttribute(LANGUAGE) == null) {
	    session.setAttribute(LANGUAGE, DEFAULT_LANGUAGE);
	    request.setAttribute(LANGUAGE, DEFAULT_LANGUAGE);
	    request.setAttribute(NEXT_LANGUAGE, nextLanguageMap.get(DEFAULT_LANGUAGE));
	    return true;//proceed the sequence
      }
      if (langParameter != null && AVAILABLE_LANGUAGES.stream().noneMatch(langParameter::equals)) {
	    response.sendError(HttpServletResponse.SC_NOT_FOUND, "No localization for given parameter");
	    return false;//we handle error by self
      }
      if (langParameter != null) {
	    session.setAttribute(LANGUAGE, langParameter);
      } else {
	    langParameter = (String) session.getAttribute(LANGUAGE);
      }
      request.setAttribute(LANGUAGE, langParameter);
      request.setAttribute(NEXT_LANGUAGE, nextLanguageMap.get(langParameter));
      return true;
}
}
