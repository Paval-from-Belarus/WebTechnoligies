package by.bsuir.poit.controller;

import by.bsuir.poit.dto.ClientDto;
import by.bsuir.poit.dto.ClientFeedbackDto;
import by.bsuir.poit.dto.LotDto;
import by.bsuir.poit.services.ClientFeedbackService;
import by.bsuir.poit.services.LotService;
import by.bsuir.poit.services.UserService;
import by.bsuir.poit.servlets.UserDetails;
import by.bsuir.poit.servlets.UserPageType;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is responsible for handling request to client page.
 * It aggregates information and supplies really jsp-servlet page to build web-client view
 *
 * @author Paval Shlyk
 * @since 07/11/2023
 */
@Controller
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientPageHandler {
private static final Logger LOGGER = LogManager.getLogger(ClientPageHandler.class);
public static final String CLIENT_ID_PARAMETER = "client_id";
public static final String PAGE_LOTS = "lots";
public static final String LOTS_PER_PAGE = "lotsPerPage";
public static final String PAGE_FEEDBACK = "lotFeedbacks";
public static final String PAGE_TYPE = "pageType";//obligatory to set for each user page the type of such page
//the last page in a list
public static final String TOTAL_PAGE_COUNT = "pagesCount";
public static final String USERNAME = "username";
public static final String RANKING = "ranking";
public static final String ACCOUNT = "account";
public static final String CURRENT_PAGE = "currentPage";

private final LotService lotService;
private final UserService userService;
private final ClientFeedbackService clientFeedbackService;
@GetMapping
public void accept(
    @RequestParam("client_id") Long clientId,
    HttpServletRequest request,
    HttpServletResponse response) throws Exception {
      UserDetails details = (UserDetails) request.getUserPrincipal();
      if (clientId == null) {
	    clientId = details.id();
      }
      Paginator paginator = new Paginator(request, 5);
      List<LotDto> clientLots = lotService.findAllBySellerId(clientId);
      List<LotDto> lots = paginator.configure(clientLots, PAGE_LOTS);
      //easily may be replaced by Hibernate lazy (or not) fetching
      Map<LotDto, ClientFeedbackDto> feedbacks = new HashMap<>();
      for (LotDto lot : lots) {
	    clientFeedbackService
		.findByLotIdAndClientTargetId(lot.getId(), clientId)
		.ifPresent(feedback -> feedbacks.put(lot, feedback));
      }
      ClientDto client = userService.findClientByUserId(clientId);
      LOGGER.trace("Following client will be depicted: {}", client.toString());
      request.setAttribute(PAGE_FEEDBACK, feedbacks);
      request.setAttribute(USERNAME, client.getName());
      request.setAttribute(RANKING, client.getRanking());
      request.setAttribute(ACCOUNT, client.getAccount());
      request.setAttribute(PageUtils.LOT_STATUSES, PageUtils.LOT_STATUSES_MAP);
      request.setAttribute(PAGE_TYPE, UserPageType.CLIENT.ordinal());
      PageUtils.includeWith(request, response, PageUtils.CLIENT_PAGE);
}
}
