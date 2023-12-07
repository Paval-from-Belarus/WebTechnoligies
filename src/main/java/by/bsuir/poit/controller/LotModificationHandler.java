package by.bsuir.poit.controller;

import by.bsuir.poit.dto.LotDto;
import by.bsuir.poit.context.RequestHandlerDefinition;
import by.bsuir.poit.services.LotService;
import by.bsuir.poit.servlets.command.RequestHandler;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

import java.util.Map;
import java.util.function.Consumer;

import static by.bsuir.poit.controller.LotModificationHandler.LotModificationType.*;

/**
 * In contract to <code>LotPublishingHandler</code> this handle is used to all other requests, which are not supported by mentioned.
 * There are all kinds of modifications
 *
 * @author Paval Shlyk
 * @see LotCreationHandler
 * @since 08/11/2023
 */
@Controller
@RequestMapping("/api/lot/update")
public class LotModificationHandler implements RequestHandler {
private static final Logger LOGGER = LogManager.getLogger(LotModificationHandler.class);

public enum LotModificationType {
      AUCTION, CUSTOMER, DELIVERY_POINT, STATUS
}

public LotModificationHandler(LotService lotService) {
      this.lotModificationMap = Map.of(
	  AUCTION, lot -> lotService.updateLotAuction(lot.getId(), lot.getAuctionId()),
	  CUSTOMER, lot -> lotService.updateLotCustomer(lot.getId(), lot.getCustomerId()),
	  DELIVERY_POINT, lot -> lotService.updateLotDeliveryPoint(lot.getId(), lot.getDeliveryPointId()),
	  STATUS, lot -> lotService.updateLotStatus(lot.getId(), lot.getStatus())
      );
}

private final Map<LotModificationType, Consumer<LotDto>> lotModificationMap;

@PostMapping
public void accept(
    @RequestPart @Validated LotDto lot,
    @RequestParam("type") @NotNull LotModificationType type,
    HttpServletResponse response) throws Exception {
      Consumer<LotDto> consumer = lotModificationMap.get(type);
      assert consumer != null;
      consumer.accept(lot);
      response.setStatus(HttpServletResponse.SC_CREATED);
}
}
