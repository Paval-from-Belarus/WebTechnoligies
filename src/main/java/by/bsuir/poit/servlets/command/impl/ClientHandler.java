package by.bsuir.poit.servlets.command.impl;

import by.bsuir.poit.bean.Client;
import by.bsuir.poit.bean.ClientFeedback;
import by.bsuir.poit.bean.Lot;
import by.bsuir.poit.bean.User;
import by.bsuir.poit.context.RequestHandlerDefinition;
import by.bsuir.poit.services.ClientFeedbackService;
import by.bsuir.poit.services.UserService;
import by.bsuir.poit.services.LotService;
import by.bsuir.poit.servlets.UserDetails;
import by.bsuir.poit.servlets.command.RequestHandler;
import by.bsuir.poit.utils.PageUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Paval Shlyk
 * @since 07/11/2023
 */
@RequestHandlerDefinition(urlPatterns = "/client")
@RequiredArgsConstructor
public class ClientHandler implements RequestHandler {
private static final Logger LOGGER = LogManager.getLogger(ClientHandler.class);
public static final String CLIENT_ID = "clientId";
public static final String PAGE_LOTS = "lots";
public static final String LOTS_PER_PAGE = "lotsPerPage";
public static final String PAGE_FEEDBACK = "lotFeedbacks";
//the last page in a list
public static final String TOTAL_PAGE_COUNT = "pagesCount";
public static final String USERNAME = "username";
public static final String RANKING = "ranking";
public static final String ACCOUNT = "account";
public static final String CURRENT_PAGE = "currentPage";

private final LotService lotService;
private final UserService userService;
private final ClientFeedbackService clientFeedbackService;

@Override
public void accept(HttpServletRequest request, HttpServletResponse response) throws Exception {
      UserDetails details = (UserDetails) request.getUserPrincipal();
      long clientId = details.id();
      if (request.getParameter(CLIENT_ID) != null) {
	    clientId = Long.parseLong(request.getParameter(CLIENT_ID));
      }
      int currentPage = 1;
      int lotsPerPage = 5;
      if (request.getParameter(CURRENT_PAGE) != null) {
	    currentPage = Integer.parseInt(request.getParameter(CURRENT_PAGE));
      }
      List<Lot> clientLots = lotService.findAllBySellerId(clientId);
      int totalPagesCount = clientLots.size() / lotsPerPage + 1;
      List<Lot> lots = clientLots.stream()
			   .skip((long) (currentPage - 1) * lotsPerPage)
			   .limit(lotsPerPage)
			   .toList();
      //easily may be replaced by Hibernate lazy (or not) fetching
      Map<Lot, ClientFeedback> feedbacks = new HashMap<>();
      for (Lot lot : lots) {
	    ClientFeedback feedback = clientFeedbackService.findByLotIdAndClientTargetId(lot.getId(), clientId);
	    feedbacks.put(lot, feedback);
      }
      Client client = userService.findClientByUserId(clientId);
      LOGGER.trace("Following client will be depicted: {}", client.toString());
      request.setAttribute(CURRENT_PAGE, currentPage);
      request.setAttribute(PAGE_LOTS, lots);
      request.setAttribute(PAGE_FEEDBACK, feedbacks);
      request.setAttribute(LOTS_PER_PAGE, lotsPerPage);
      request.setAttribute(TOTAL_PAGE_COUNT, totalPagesCount);
      request.setAttribute(USERNAME, client.getName());
      request.setAttribute(RANKING, client.getRanking());
      request.setAttribute(ACCOUNT, client.getAccount());
      request.setAttribute(PageUtils.LOT_STATUSES, PageUtils.LOT_STATUSES_MAP);
      PageUtils.includeWith(request, response, PageUtils.CLIENT_PAGE);
}
}
