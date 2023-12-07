package by.bsuir.poit.controller;

import by.bsuir.poit.dto.*;
import by.bsuir.poit.context.RequestHandlerDefinition;
import by.bsuir.poit.services.AuctionService;
import by.bsuir.poit.services.LotService;
import by.bsuir.poit.services.UserService;
import by.bsuir.poit.services.exception.authorization.UserNotFoundException;
import by.bsuir.poit.services.exception.resources.ResourceNotFoundException;
import by.bsuir.poit.servlets.UserDetails;
import by.bsuir.poit.servlets.command.RequestHandler;
import by.bsuir.poit.utils.PageUtils;
import by.bsuir.poit.utils.Paginator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

/**
 * This one handles request to basic info about auction (if it may be possible I'd inject <code>@PathVariable</code> but it's too hard to implement).
 * The current implementation should fetch id auction explicitly
 *
 * @author Paval Shlyk
 * @since 20/11/2023
 */

@Controller
@RequestMapping("/api/auction/info")
@RequiredArgsConstructor
public class AuctionInfoPageHandler {
private static final Logger LOGGER = LogManager.getLogger(AuctionInfoPageHandler.class);
public static final String AUCTION_ID_PARAMETER = "auction_id";
//the parameters for jsp page
//the common info about auction
public static final String AUCTION_TYPE_ID = "auctionType";
public static final String AUCTION_ID = "auctionId";
public static final String PAGE_TYPE = "pageType";
public static final String EVENT_DATE = "eventDate";
public static final String MEMBER_LIMIT = "memberLimit";
public static final String PRICE_STEP = "priceStep";
public static final String USER_NAME = "username";
public static final String LOT_LIST = "lotList";
//auction specific info
public static final String BLIND_AUCTION_BET_LIMIT = "blindAuctionBetLimit";
public static final String BLIND_AUCTION_TIMEOUT = "blindAuctionTimeout";
public static final String BLITZ_AUCTION_EXCLUDE_COUNT = "blitzAuctionExcludeCount";
private final Map<Class<? extends AuctionDto>, BiConsumer<HttpServletRequest, AuctionDto>> requestSpecificAuctionHandlerMap = Map.of(
    BlitzAuctionDto.class, this::putBlitzAuctionInfo,
    BlindAuctionDto.class, this::putBlindAuctionInfo
);
private final AuctionService auctionService;
private final UserService userService;
private final LotService lotService;

@GetMapping
public void accept(
    @RequestParam("auction_id") long auctionId,
    HttpServletRequest request,
    HttpServletResponse response) throws Exception {
      try {
	    UserDetails details = (UserDetails) request.getUserPrincipal();
	    AuctionDto auction = auctionService.findById(auctionId);
	    AuctionTypeDto type = auctionService.findTypeByAuctionId(auction.getId());
	    List<LotDto> allLots = lotService.findAllByAuction(auction.getId());
	    Paginator paginator = new Paginator(request, 5);
	    List<LotDto> pageLots = paginator.configure(allLots, LOT_LIST);
	    request.setAttribute(AUCTION_TYPE_ID, type.getId());
	    request.setAttribute(EVENT_DATE, auction.getLastRegisterDate());
	    if (auction.getMembersLimit() != null) {
		  request.setAttribute(MEMBER_LIMIT, auction.getMembersLimit());
	    }
	    request.setAttribute(PAGE_TYPE, PageUtils.typeOfRole(details.role()).ordinal());
	    request.setAttribute(PRICE_STEP, auction.getPriceStep());
//	    request.setAttribute(USER_NAME, user.getName());
	    request.setAttribute(LOT_LIST, pageLots);
	    request.setAttribute(PageUtils.LOT_STATUSES, PageUtils.LOT_STATUSES_MAP);
	    request.setAttribute(AUCTION_ID, auction.getId());
	    BiConsumer<HttpServletRequest, AuctionDto> handler = requestSpecificAuctionHandlerMap.get(auction.getClass());
	    if (handler != null) {
		  handler.accept(request, auction);
	    }
	    PageUtils.includeWith(request, response, PageUtils.AUCTION);
      } catch (ResourceNotFoundException e) {
	    String msg = String.format("The auction by given id=%s is not present", auctionId);
	    LOGGER.info(msg);
	    response.sendError(HttpServletResponse.SC_NOT_FOUND, msg);
      } catch (UserNotFoundException e) {
	    String msg = String.format("The admin for auction for given id=%d is not set", auctionId);
	    LOGGER.error(msg);
	    throw new IllegalStateException(msg);
      }
}

private void putBlitzAuctionInfo(HttpServletRequest request, AuctionDto origin) {
      assert origin instanceof BlitzAuctionDto;
      BlitzAuctionDto auction = (BlitzAuctionDto) origin;
      request.setAttribute(BLITZ_AUCTION_EXCLUDE_COUNT, auction.getMemberExcludeLimit());
}

private void putBlindAuctionInfo(HttpServletRequest request, AuctionDto origin) {
      assert origin instanceof BlindAuctionDto;
      BlindAuctionDto auction = (BlindAuctionDto) origin;
      request.setAttribute(BLIND_AUCTION_TIMEOUT, auction.getTimeout());
      request.setAttribute(BLIND_AUCTION_BET_LIMIT, auction.getBetLimit());
}

private Optional<Long> parseAuctionId(HttpServletRequest request) {
      Optional<Long> result = Optional.empty();
      try {
	    Long auctionId = Long.parseLong(request.getParameter(AUCTION_ID_PARAMETER));
	    result = Optional.of(auctionId);
      } catch (Exception ignored) {
      }
      return result;
}
}
