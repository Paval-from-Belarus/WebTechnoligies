package by.bsuir.poit.servlets.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Paval Shlyk
 * @since 27/10/2023
 */
@FunctionalInterface
public interface RequestHandler {
void accept(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
