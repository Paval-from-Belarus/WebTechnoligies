package by.bsuir.poit.servlets.command.impl;

import by.bsuir.poit.bean.Lot;
import by.bsuir.poit.context.RequestHandlerDefinition;
import by.bsuir.poit.services.LotService;
import by.bsuir.poit.servlets.command.RequestHandler;
import by.bsuir.poit.utils.ParserUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.desktop.ScreenSleepEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static by.bsuir.poit.servlets.command.impl.LotModificationHandler.LotModificationType.*;

/**
 * In contract to <code>LotPublishingHandler</code> this handle is used to all other requests, which are not supported by mentioned.
 * There are all kinds of modifications
 *
 * @author Paval Shlyk
 * @see LotPublishingHandler
 * @since 08/11/2023
 */
@RequestHandlerDefinition(urlPatterns = "/lot/modify")
@RequiredArgsConstructor
public class LotModificationHandler implements RequestHandler {
private static final Logger LOGGER = LogManager.getLogger(LotModificationHandler.class);
private final LotService lotService;
public static final String LOT_MODIFICATION_PARAMETER = "type";

public enum LotModificationType {
      AUCTION, CUSTOMER, DELIVERY_POINT, STATUS
}

public LotModificationHandler(LotService lotService) {
      this.lotService = lotService;
      this.lotModificationMap = Map.of(
	  AUCTION, lot -> lotService.updateLotAuction(lot.getId(), lot.getAuctionId()),
	  CUSTOMER, lot -> lotService.updateLotCustomer(lot.getId(), lot.getCustomerId()),
	  DELIVERY_POINT, lot -> lotService.updateLotDeliveryPoint(lot.getId(), lot.getDeliveryPointId()),
	  STATUS, lot -> lotService.updateLotStatus(lot.getId(), lot.getStatus())
      );
}

private final Map<LotModificationType, Consumer<Lot>> lotModificationMap;

@Override
public void accept(HttpServletRequest request, HttpServletResponse response) throws Exception {
      Lot lot = ParserUtils.parseLot(request);
      LotModificationType type = ParserUtils
				     .parseEnum(LotModificationType.class, request.getParameter(LOT_MODIFICATION_PARAMETER))
				     .orElseThrow(() -> {
					   final String msg = String.format("Invalid parameters for lot handler. Impossible to perform modification by such type: %s", request.getParameter(LOT_MODIFICATION_PARAMETER));
					   LOGGER.error(msg);
					   return new IllegalStateException(msg);
				     });
      Consumer<Lot> consumer = lotModificationMap.get(type);
      assert consumer != null;
      consumer.accept(lot);
      response.setStatus(HttpServletResponse.SC_CREATED);
}
}
