package by.bsuir.poit.controller;

import by.bsuir.poit.services.AuctionService;
import by.bsuir.poit.services.exception.authorization.UserAccessViolationException;
import by.bsuir.poit.utils.ControllerUtils;
import by.bsuir.poit.utils.PageUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

/**
 * Responsible for supplement of attributes assignment jsp page (page where admin can assign a lot to chosen auction)
 *
 * @author Paval Shlyk
 * @since 23/11/2023
 */
@Controller
@RequestMapping("/api/lot/assign")
@RequiredArgsConstructor
public class LotAssignmentHandler {
private static final Logger LOGGER = LogManager.getLogger(LotAssignmentHandler.class);
private final AuctionService auctionService;

@PostMapping
public void accept(
    @RequestParam("auction_id") long auctionId,
    @RequestPart("lot_id") long lotId,
    HttpServletRequest request,
    HttpServletResponse response) throws Exception {
      try {
	    auctionService.assignLot(request.getUserPrincipal(), auctionId, lotId);
      } catch (UserAccessViolationException e) {
	    LOGGER.info("Attempt to assign lot to auction by unauthorized user={}", request.getUserPrincipal());
	    response.sendError(HttpServletResponse.SC_FORBIDDEN);
	    return;
      }
      PageUtils.redirectTo(response, ControllerUtils.ASSIGNMENT_ENDPOINT + "?auction_id=" + auctionId);
}
}
