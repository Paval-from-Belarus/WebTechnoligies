package by.bsuir.poit.servlets.command.impl;

import by.bsuir.poit.context.RequestHandlerDefinition;
import by.bsuir.poit.services.LotService;
import by.bsuir.poit.servlets.command.RequestHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * @author Paval Shlyk
 * @since 08/11/2023
 */
@RequestHandlerDefinition(urlPatterns = "/lot")
@RequiredArgsConstructor
public class LotHandler implements RequestHandler {
private final LotService lotService;
@Override
public void accept(HttpServletRequest request, HttpServletResponse response) throws Exception {

}
}
