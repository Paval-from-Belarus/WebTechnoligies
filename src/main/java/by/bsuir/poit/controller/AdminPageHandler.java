package by.bsuir.poit.controller;

import by.bsuir.poit.dto.AuctionDto;
import by.bsuir.poit.dto.UserDto;
import by.bsuir.poit.model.User;
import by.bsuir.poit.services.AuctionService;
import by.bsuir.poit.services.AuthorizationService;
import by.bsuir.poit.services.UserService;
import by.bsuir.poit.utils.PageUtils;
import by.bsuir.poit.utils.Paginator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

/**
 * This class is responsible for handling requests related to the admin page. It retrieves the user principal from the request, verifies the user's access level, and retrieves the admin user information. It also provides pagination functionality for displaying a list of auctions associated with the admin user. The admin username is set as a request attribute, and the admin page is included in the response.
 *
 * @author Paval Shlyk
 * @since 08/11/2023
 */

@Controller
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminPageHandler {
public static final String AUCTIONS = "auctionList";
public static final String USERNAME = "username";
private final AuthorizationService authorizationService;
private final UserService userService;
private final AuctionService auctionService;

@GetMapping
public void accept(
    @NotNull Principal principal,
    HttpServletRequest request,
    HttpServletResponse response) throws Exception {
      authorizationService.verifyByUserAccess(principal, User.ADMIN);
      long adminId = authorizationService.getUserIdByPrincipal(principal);
      UserDto admin = userService.findUserByUserId(adminId);
      Paginator paginator = new Paginator(request, 5);
      List<AuctionDto> allAuctions = auctionService.findHeadersByPrincipal(adminId);
      paginator.configure(allAuctions, AUCTIONS);
      request.setAttribute(USERNAME, admin.getName());
      PageUtils.includeWith(request, response, PageUtils.ADMIN_PAGE);
}
}
