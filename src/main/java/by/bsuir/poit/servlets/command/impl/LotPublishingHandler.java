package by.bsuir.poit.servlets.command.impl;

import by.bsuir.poit.bean.Lot;
import by.bsuir.poit.context.RequestHandlerDefinition;
import by.bsuir.poit.services.LotService;
import by.bsuir.poit.servlets.command.RequestHandler;
import by.bsuir.poit.utils.ParserUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * The handler is supposed to be request handler for any action on lot.
 * But the basic functionality is publishing of a new lot.
 *
 * @author Paval Shlyk
 * @since 08/11/2023
 */
@RequestHandlerDefinition(urlPatterns = "/lot/publish")
@RequiredArgsConstructor
public class LotPublishingHandler implements RequestHandler {
private final LotService lotService;
@Override
public void accept(HttpServletRequest request, HttpServletResponse response) throws Exception {
      Lot lot = ParserUtils.parse(Lot.class, request);
      lotService.save(lot);
      response.setStatus(HttpServletResponse.SC_CREATED);
}

}
