package by.bsuir.poit.controller;

import by.bsuir.poit.dto.AuctionDto;
import by.bsuir.poit.dto.LotDto;
import by.bsuir.poit.dto.UserDto;
import by.bsuir.poit.context.RequestHandlerDefinition;
import by.bsuir.poit.model.User;
import by.bsuir.poit.services.AuctionService;
import by.bsuir.poit.services.LotService;
import by.bsuir.poit.services.UserService;
import by.bsuir.poit.servlets.UserDetails;
import by.bsuir.poit.servlets.UserPageType;
import by.bsuir.poit.servlets.command.RequestHandler;
import by.bsuir.poit.utils.PageUtils;
import by.bsuir.poit.utils.Paginator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * This class is responsible for handling requests related to auction assignment page.
 * It retrieves the necessary data from the database and sets the attributes in the request object.
 * If the user is not an admin, it sends a forbidden error response.
 * It uses {@link Paginator} to paginate the list of lots.
 * The assigned auction and the admin user details are set as attributes in the request object.
 * Finally, it includes the auction assignment page in the response.
 *
 * @author Paval Shlyk
 * @since 22/11/2023
 */
@Controller
@RequestMapping("/api/auction/assign")
@RequiredArgsConstructor
public class AuctionAssignmentPageHandler {
private static final Logger LOGGER = LogManager.getLogger(AdminPageHandler.class);
public static final String AUCTION_ID = "auctionId";
public static final String PAGE_TYPE = "pageType";
public static final String PAGE_LOTS = "lots";
public static final String USERNAME = "username";

private final LotService lotService;
private final UserService userService;
private final AuctionService auctionService;

@PostMapping
public void assign(
    @RequestParam("auction_id") long auctionId,
    HttpServletRequest request,
    HttpServletResponse response) throws Exception {
      UserDetails details = (UserDetails) request.getUserPrincipal();
      if (details.role() != User.ADMIN) {
	    response.sendError(HttpServletResponse.SC_FORBIDDEN, "You cannot see such page");
	    return;
      }
      AuctionDto auction = auctionService.findById(auctionId);
      long adminId = details.id();
      Paginator paginator = new Paginator(request, 5);
      List<LotDto> uncommittedLots = lotService.findAllBeforeAuctionLots().stream()
				      .filter(lot -> lot.getAuctionTypeId().equals(auction.getAuctionTypeId()))//it's filthy
				      .toList();
      paginator.configure(uncommittedLots, PAGE_LOTS);
      UserDto user = userService.findUserByUserId(adminId);
      request.setAttribute(USERNAME, user.getName());
      request.setAttribute(PAGE_TYPE, UserPageType.ADMIN.ordinal());
      request.setAttribute(AUCTION_ID, auctionId);
      //recursively returns to the same page
      PageUtils.includeWith(request, response, PageUtils.AUCTION_ASSIGNMENT_PAGE);
}
}
