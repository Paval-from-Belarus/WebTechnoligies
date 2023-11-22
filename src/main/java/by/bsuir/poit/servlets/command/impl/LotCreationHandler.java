package by.bsuir.poit.servlets.command.impl;

import by.bsuir.poit.bean.Lot;
import by.bsuir.poit.context.RequestHandlerDefinition;
import by.bsuir.poit.services.LotService;
import by.bsuir.poit.servlets.UserDetails;
import by.bsuir.poit.servlets.command.RequestHandler;
import by.bsuir.poit.utils.ControllerUtils;
import by.bsuir.poit.utils.PageUtils;
import by.bsuir.poit.utils.ParserUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.security.Principal;

/**
 * The handler is supposed to be request handler for any action on lot.
 * But the basic functionality is publishing of a new lot.
 *
 * @author Paval Shlyk
 * @since 08/11/2023
 */
@RequestHandlerDefinition(urlPatterns = "/lot/new")
@RequiredArgsConstructor
public class LotCreationHandler implements RequestHandler {
private final LotService lotService;

@Override
public void accept(HttpServletRequest request, HttpServletResponse response) throws Exception {
      Lot lot = ParserUtils.parseLot(request);
      lotService.save(request.getUserPrincipal(), lot);
      //the functionality to create a new lot is available only for admin
      PageUtils.redirectTo(response, ControllerUtils.CLIENT_ENDPOINT);
}

}
