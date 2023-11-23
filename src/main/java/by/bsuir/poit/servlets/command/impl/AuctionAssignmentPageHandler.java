package by.bsuir.poit.servlets.command.impl;

import by.bsuir.poit.bean.Auction;
import by.bsuir.poit.bean.Lot;
import by.bsuir.poit.bean.User;
import by.bsuir.poit.context.RequestHandlerDefinition;
import by.bsuir.poit.services.AuctionService;
import by.bsuir.poit.services.LotService;
import by.bsuir.poit.services.UserService;
import by.bsuir.poit.servlets.UserDetails;
import by.bsuir.poit.servlets.UserPageType;
import by.bsuir.poit.servlets.command.RequestHandler;
import by.bsuir.poit.utils.PageUtils;
import by.bsuir.poit.utils.Paginator;
import by.bsuir.poit.utils.ParserUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * @author Paval Shlyk
 * @since 22/11/2023
 */
@RequiredArgsConstructor
@RequestHandlerDefinition(urlPatterns = "/auction/assign")
public class AuctionAssignmentPageHandler implements RequestHandler {
private static final Logger LOGGER = LogManager.getLogger(AdminPageHandler.class);
public static final String AUCTION_ID = "auctionId";
public static final String PAGE_TYPE = "pageType";
public static final String PAGE_LOTS = "lots";
public static final String USERNAME = "username";

private final LotService lotService;
private final UserService userService;
private final AuctionService auctionService;

@Override
public void accept(HttpServletRequest request, HttpServletResponse response) throws Exception {
      UserDetails details = (UserDetails) request.getUserPrincipal();
      if (details.role() != User.ADMIN) {
	    response.sendError(HttpServletResponse.SC_FORBIDDEN, "You cannot see such page");
	    return;
      }
      long auctionId = ParserUtils.parseRequest(Long.class, request, ParserUtils.AUCTION_ID).orElseThrow(() -> {
	    final String msg = String.format("Invalid auctionId=%s for assignment page", request.getParameter(ParserUtils.AUCTION_TYPE_ID));
	    LOGGER.info(msg);
	    return new IllegalStateException(msg);
      });
      Auction auction = auctionService.findById(auctionId);
      long adminId = details.id();
      Paginator paginator = new Paginator(request, 5);
      List<Lot> uncommittedLots = lotService.findAllBeforeAuctionLots().stream()
				      .filter(lot -> lot.getAuctionTypeId().equals(auction.getAuctionTypeId()))//it's filthy
				      .toList();
      paginator.configure(uncommittedLots, PAGE_LOTS);
      User user = userService.findUserByUserId(adminId);
      request.setAttribute(USERNAME, user.getName());
      request.setAttribute(PAGE_TYPE, UserPageType.ADMIN.ordinal());
      request.setAttribute(AUCTION_ID, auctionId);
      //recursively returns to the same page
      PageUtils.includeWith(request, response, PageUtils.AUCTION_ASSIGNMENT_PAGE);
}
}
