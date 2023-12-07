package by.bsuir.poit.servlets.filters;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author Paval Shlyk
 * @since 07/12/2023
 */
public class AuthorizationInterceptor implements HandlerInterceptor {
@Override
public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
      return HandlerInterceptor.super.preHandle(request, response, handler);
}
}
