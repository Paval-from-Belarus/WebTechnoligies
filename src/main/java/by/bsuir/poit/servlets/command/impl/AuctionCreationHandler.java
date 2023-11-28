package by.bsuir.poit.servlets.command.impl;

import by.bsuir.poit.dto.Auction;
import by.bsuir.poit.context.RequestHandlerDefinition;
import by.bsuir.poit.services.AuctionService;
import by.bsuir.poit.servlets.command.RequestHandler;
import by.bsuir.poit.utils.ControllerUtils;
import by.bsuir.poit.utils.PageUtils;
import by.bsuir.poit.utils.ParserUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * @author Paval Shlyk
 * @since 27/10/2023
 */
@RequiredArgsConstructor
@RequestHandlerDefinition(urlPatterns = "/auction/new")
public class AuctionCreationHandler implements RequestHandler {
private final AuctionService auctionService;
@Override
public void accept(HttpServletRequest request, HttpServletResponse response) throws Exception {
      Auction auction = ParserUtils.parseAuction(request);
      auctionService.saveAuction(request.getUserPrincipal(), auction);
      PageUtils.redirectTo(response, ControllerUtils.ADMIN_ENDPOINT);
}
}
