package by.bsuir.poit.utils;

import by.bsuir.poit.bean.AuctionBet;
import by.bsuir.poit.bean.Lot;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.LongFunction;

/**
 * @author Paval Shlyk
 * @since 08/11/2023
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ParserUtils {
private static final Logger LOGGER = LogManager.getLogger(ParserUtils.class);
//the information about lot
public static final String LOT_TITLE = "lot_title";
public static final String LOT_ID = "lot_id";
public static final String AUCTION_TYPE_ID = "auction_type_id";
public static final String LOT_STATUS = "lot_status";
public static final String LOT_SELLER_ID = "seller_id";
public static final String LOT_CUSTOMER_ID = "lot_customer_id";
public static final String LOT_START_PRICE = "lot_start_price";

public static Lot parseLot(HttpServletRequest request) {
      var builder = Lot.builder();//the setting to initial status on parse stage is not a valid
      try {
	    if (request.getParameter(LOT_TITLE) != null) {
		  builder.title(request.getParameter(LOT_TITLE));
	    }
	    parseRequestParameter(Long.class, request, LOT_ID)
		.ifPresent(builder::id);
	    parseRequestParameter(Long.class, request, LOT_CUSTOMER_ID)
		.ifPresent(builder::customerId);
	    parseRequestParameter(Long.class, request, AUCTION_TYPE_ID)
		.ifPresent(builder::auctionTypeId);
	    parseRequestParameter(Short.class, request, LOT_STATUS)
		.ifPresent(builder::status);
	    parseRequestParameter(Long.class, request, LOT_SELLER_ID)
		.ifPresent(builder::sellerId);
	    parseRequestParameter(Double.class, request, LOT_START_PRICE)
		.ifPresent(builder::startPrice);
      } catch (NumberFormatException e) {
	    LOGGER.error("Failed to parse lot from request parameters {}", e.toString());
	    throw new IllegalStateException(e);
      }
      return builder.build();
}

//the information about auction bet
public static final String AUCTION_BET_VALUE = "bet";
public static final String AUCTION_ID = "auction_id";

public static AuctionBet parseBet(HttpServletRequest request) {
      var builder = AuctionBet.builder();
      try {
	    parseRequestParameter(Double.class, request, AUCTION_BET_VALUE)
		.ifPresent(builder::bet);
	    parseRequestParameter(Long.class, request, AUCTION_ID)
		.ifPresent(builder::auctionId);
	    parseRequestParameter(Long.class, request, LOT_ID)
		.ifPresent(builder::lotId);
	    //all other fields are set via upper level
      } catch (NumberFormatException e) {
	    LOGGER.error("Failed to pars auction bet from request parameters");
	    throw new IllegalStateException(e);
      }
      return builder.build();
}

private static <T extends Number> Optional<T> parseRequestParameter(Class<T> clazz, HttpServletRequest request, String parameter) throws NumberFormatException {
      if (request.getParameter(parameter) == null) {
	    return Optional.empty();
      }
      return Optional.of(parse(clazz, request.getParameter(parameter)));
}

@SuppressWarnings("unchecked")
private static <T extends Number> T parse(Class<T> clazz, String value) throws NumberFormatException {
      final Map<Class<?>, Function<String, ?>> map = Map.of(
	  Double.class, Double::parseDouble,
	  Long.class, Long::parseLong,
	  Integer.class, Integer::parseInt,
	  Short.class, Short::parseShort,
	  Byte.class, Byte::parseByte
      );
      Function<String, ?> parser = map.get(clazz);
      assert parser != null;
      return (T) parser.apply(value);
}

public static <T extends Enum<?>> Optional<T> parseEnum(Class<T> clazz, String value) {
      if (value == null) {
	    return Optional.empty();
      }
      value = value.toUpperCase().replace("-", "_");
      for (T constant : clazz.getEnumConstants()) {
	    if (constant.name().equals(value)) {
		  return Optional.of(constant);
	    }
      }
      return Optional.empty();
}
}
