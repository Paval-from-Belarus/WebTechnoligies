package by.bsuir.poit.servlets.command.impl;

import by.bsuir.poit.bean.AuctionBet;
import by.bsuir.poit.context.RequestHandlerDefinition;
import by.bsuir.poit.services.AuctionService;
import by.bsuir.poit.services.exception.resources.ResourceModifyingException;
import by.bsuir.poit.services.exception.resources.ResourceNotFoundException;
import by.bsuir.poit.servlets.UserDetails;
import by.bsuir.poit.servlets.command.RequestHandler;
import by.bsuir.poit.utils.ParserUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Paval Shlyk
 * @since 12/11/2023
 */
@RequestHandlerDefinition(urlPatterns = "/auction/bet/new")
@RequiredArgsConstructor
public class AuctionBetCreationHandler implements RequestHandler {
private static final Logger LOGGER = LogManager.getLogger(AuctionBetCreationHandler.class);
private final AuctionService auctionService;

@Override
public void accept(HttpServletRequest request, HttpServletResponse response) throws Exception {
      AuctionBet auctionBet = ParserUtils.parseBet(request);
      UserDetails principal = (UserDetails) request.getUserPrincipal();
      assert principal != null;
      auctionBet.setClientId(principal.id());
      auctionBet.setTime(new Timestamp(new Date().getTime()));
      try {
	    auctionService.saveBet(auctionBet);
      } catch (ResourceNotFoundException e) {
	    final String msg = String.format("Failed to save bet for user=%d with size=%f", principal.id(), auctionBet.getBet());
	    LOGGER.error(msg);
	    response.sendError(HttpServletResponse.SC_NOT_FOUND);
      }
}
}
