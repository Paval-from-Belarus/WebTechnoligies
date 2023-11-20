package by.bsuir.poit.servlets.command.impl;

import by.bsuir.poit.bean.Lot;
import by.bsuir.poit.bean.User;
import by.bsuir.poit.bean.mappers.AuctionMapperImpl;
import by.bsuir.poit.context.RequestHandlerDefinition;
import by.bsuir.poit.services.LotService;
import by.bsuir.poit.services.UserService;
import by.bsuir.poit.servlets.UserDetails;
import by.bsuir.poit.servlets.command.RequestHandler;
import by.bsuir.poit.utils.PageUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Paval Shlyk
 * @since 08/11/2023
 */
@RequestHandlerDefinition(urlPatterns = "/admin")
@RequiredArgsConstructor
public class AdminHandler implements RequestHandler {
private static final Logger LOGGER = LogManager.getLogger(AdminHandler.class);
public static final String ADMIN_ID = "adminId";
public static final String PAGE_LOTS = "lots";
public static final String USERNAME = "username";
public static final String CURRENT_PAGE = "currentPage";
public static final String TOTAL_PAGE_COUNT = "pagesCount";
public static final String LOTS_PER_PAGE = "lotsPerPage";

private final LotService lotService;
private final UserService userService;

@Override
public void accept(HttpServletRequest request, HttpServletResponse response) throws Exception {
      UserDetails details = (UserDetails) request.getUserPrincipal();
      if (details.role() != User.ADMIN) {
	    response.sendError(HttpServletResponse.SC_FORBIDDEN, "You cannot see such page");
	    return;
      }
      long adminId = details.id();
      int currentPage = 1;
      int lotsPerPage = 5;
      if (request.getParameter(CURRENT_PAGE) != null) {
	    currentPage = Integer.parseInt(request.getParameter(CURRENT_PAGE));
      }
      List<Lot> uncommittedLots = lotService.findAllByStatus(Lot.BEFORE_AUCTION_STATUS);
      int totalPagesCount = uncommittedLots.size() / lotsPerPage + 1;
      List<Lot> pageLots = uncommittedLots.stream()
			       .skip((long) (currentPage - 1) * lotsPerPage)
			       .limit(lotsPerPage)
			       .toList();
      User user = userService.findUserByUserId(adminId);
      request.setAttribute(CURRENT_PAGE, currentPage);
      request.setAttribute(LOTS_PER_PAGE, lotsPerPage);
      request.setAttribute(ADMIN_ID, adminId);
      request.setAttribute(USERNAME, user.getName());
      request.setAttribute(PAGE_LOTS, pageLots);
      request.setAttribute(TOTAL_PAGE_COUNT, totalPagesCount);
      PageUtils.includeWith(request, response, PageUtils.ADMIN_PAGE);
}
}
