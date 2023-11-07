package by.bsuir.poit.servlets.command.impl;

import by.bsuir.poit.bean.Client;
import by.bsuir.poit.bean.ClientFeedback;
import by.bsuir.poit.bean.Lot;
import by.bsuir.poit.context.RequestHandlerDefinition;
import by.bsuir.poit.services.ClientService;
import by.bsuir.poit.services.LotService;
import by.bsuir.poit.servlets.UserDetails;
import by.bsuir.poit.servlets.command.RequestHandler;
import by.bsuir.poit.utils.PageUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @author Paval Shlyk
 * @since 07/11/2023
 */
@RequestHandlerDefinition(urlPatterns = "/client")
@RequiredArgsConstructor
public class ClientHandler implements RequestHandler {
public static final String PAGE_LOTS = "lots";
public static final String PAGE_FEEDBACK = "feedbacks";
public static final String USERNAME = "username";
public static final String RANKING = "ranking";
public static final String ACCOUNT = "account";
private final LotService lotService;
private final ClientService clientService;

@Override
public void accept(HttpServletRequest request, HttpServletResponse response) throws Exception {
      UserDetails details = (UserDetails) request.getUserPrincipal();
      int lotsPage = 1;
      int lotsPerPage = 5;
      int feedbackPage = 1;
      int feedbackPerPage = 5;
      if (request.getParameter(PAGE_LOTS) != null) {
	    lotsPage = Integer.parseInt(request.getParameter(PAGE_LOTS));
      }
      if (request.getParameter(PAGE_FEEDBACK) != null) {
	    feedbackPage = Integer.parseInt(request.getParameter(PAGE_FEEDBACK));
      }
      List<Lot> lots = lotService.findAllByClient(details.id()).stream()
			   .skip((long) (lotsPage - 1) * lotsPerPage)
			   .limit(lotsPerPage)
			   .toList();
      List<ClientFeedback> feedbacks = clientService.findAllFeedbacksBySellerId(details.id()).stream()
					   .skip((long) (feedbackPage - 1) * feedbackPerPage)
					   .limit(feedbackPerPage)
					   .toList();
      Client client = clientService.findClientByUserId(details.id());
      request.setAttribute(PAGE_LOTS, lots);
      request.setAttribute(PAGE_FEEDBACK, feedbacks);
      request.setAttribute(USERNAME, client.getName());
      request.setAttribute(RANKING, client.getRanking());
      request.setAttribute(ACCOUNT, client.getAccount());
      PageUtils.forwardTo(request, response, PageUtils.CLIENT_PAGE);
}
}
