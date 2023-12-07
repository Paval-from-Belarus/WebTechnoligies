package by.bsuir.poit.controller;

import by.bsuir.poit.context.RequestHandlerDefinition;
import by.bsuir.poit.services.LotService;
import by.bsuir.poit.servlets.command.RequestHandler;
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

/**
 * @author Paval Shlyk
 * @since 21/11/2023
 */
@Controller
@RequestMapping("/api/lot/delete")
@RequiredArgsConstructor
public class LotDeletionHandler {
private static final Logger LOGGER = LogManager.getLogger(LotDeletionHandler.class);
private final LotService lotService;

@PostMapping
public void accept(
    @RequestParam("lot_id") long lotId,
    HttpServletRequest request,
    HttpServletResponse response) throws Exception {
      boolean isDeleted = lotService.deleteIfPossible(lotId);//when method throws ResourceBusyException, we cannot do anything
      if (!isDeleted) {
	    response.sendError(HttpServletResponse.SC_FORBIDDEN);
      } else {
	    PageUtils.redirectTo(response, PageUtils.USER_PAGE);
      }
}
}
