package by.bsuir.poit.servlets.command.impl;

import by.bsuir.poit.bean.Auction;
import by.bsuir.poit.bean.User;
import by.bsuir.poit.context.RequestHandlerDefinition;
import by.bsuir.poit.services.AuctionService;
import by.bsuir.poit.services.AuthorizationService;
import by.bsuir.poit.services.UserService;
import by.bsuir.poit.servlets.command.RequestHandler;
import by.bsuir.poit.utils.PageUtils;
import by.bsuir.poit.utils.Paginator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.security.Principal;
import java.util.List;

/**
 * @author Paval Shlyk
 * @since 08/11/2023
 */
@RequestHandlerDefinition(urlPatterns = "/admin")
@RequiredArgsConstructor
public class AdminPageHandler implements RequestHandler {
public static final String AUCTIONS = "auctionList";
public static final String USERNAME = "username";
private final AuthorizationService authorizationService;
private final UserService userService;
private final AuctionService auctionService;
@Override
public void accept(HttpServletRequest request, HttpServletResponse response) throws Exception {
      Principal principal = request.getUserPrincipal();
      authorizationService.verifyByUserAccess(principal, User.ADMIN);
      long adminId = authorizationService.getUserIdByPrincipal(principal);
      User admin = userService.findUserByUserId(adminId);
      Paginator paginator = new Paginator(request, 5);
      List<Auction> allAuctions = auctionService.findHeadersByAdminId(adminId);
      paginator.configure(allAuctions, AUCTIONS);
      request.setAttribute(USERNAME, admin.getName());
      PageUtils.includeWith(request, response, PageUtils.ADMIN_PAGE);
}
}
