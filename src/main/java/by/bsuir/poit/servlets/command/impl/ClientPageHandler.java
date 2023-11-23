package by.bsuir.poit.servlets.command.impl;

import by.bsuir.poit.bean.Client;
import by.bsuir.poit.bean.ClientFeedback;
import by.bsuir.poit.bean.Lot;
import by.bsuir.poit.context.RequestHandlerDefinition;
import by.bsuir.poit.services.ClientFeedbackService;
import by.bsuir.poit.services.UserService;
import by.bsuir.poit.services.LotService;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Paval Shlyk
 * @since 07/11/2023
 */
@RequestHandlerDefinition(urlPatterns = "/client")
@RequiredArgsConstructor
public class ClientPageHandler implements RequestHandler {
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

@Override
public void accept(HttpServletRequest request, HttpServletResponse response) throws Exception {
      UserDetails details = (UserDetails) request.getUserPrincipal();
      long clientId = details.id();
      if (request.getParameter(CLIENT_ID_PARAMETER) != null) {
	    clientId = Long.parseLong(request.getParameter(CLIENT_ID_PARAMETER));
      }
      Paginator paginator = new Paginator(request, 5);
      List<Lot> clientLots = lotService.findAllBySellerId(clientId);
      List<Lot> lots = paginator.configure(clientLots, PAGE_LOTS);
      //easily may be replaced by Hibernate lazy (or not) fetching
      Map<Lot, ClientFeedback> feedbacks = new HashMap<>();
      for (Lot lot : lots) {
	    clientFeedbackService
		.findByLotIdAndClientTargetId(lot.getId(), clientId)
		.ifPresent(feedback -> feedbacks.put(lot, feedback));
      }
      Client client = userService.findClientByUserId(clientId);
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
