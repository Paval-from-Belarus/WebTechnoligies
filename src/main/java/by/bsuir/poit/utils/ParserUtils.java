package by.bsuir.poit.utils;

import by.bsuir.poit.dto.Auction;
import by.bsuir.poit.dto.AuctionBet;
import by.bsuir.poit.dto.Lot;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

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

/**
 * Parses the lot's information from the HttpServletRequest.
 *
 * @param request The HttpServletRequest object.
 * @return The parsed Lot object.
 */
public static Lot parseLot(HttpServletRequest request) {
      var builder = Lot.builder();//the setting to initial status on parse stage is not a valid
      try {
	    if (request.getParameter(LOT_TITLE) != null) {
		  builder.title(request.getParameter(LOT_TITLE));
	    }
	    parseRequest(Long.class, request, LOT_ID)
		.ifPresent(builder::id);
	    parseRequest(Long.class, request, LOT_CUSTOMER_ID)
		.ifPresent(builder::customerId);
	    parseRequest(Long.class, request, AUCTION_TYPE_ID)
		.ifPresent(builder::auctionTypeId);
	    parseRequest(Short.class, request, LOT_STATUS)
		.ifPresent(builder::status);
	    parseRequest(Long.class, request, LOT_SELLER_ID)
		.ifPresent(builder::sellerId);
	    parseRequest(Double.class, request, LOT_START_PRICE)
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
public static final String PRICE_STEP = "price_step";
public static final String EVENT_DATE = "event_date";

/**
 * Parses the auction bet information from the HttpServletRequest.
 *
 * @param request The HttpServletRequest object.
 * @return The parsed AuctionBet object.
 */
public static AuctionBet parseBet(HttpServletRequest request) {
      var builder = AuctionBet.builder();
      try {
	    parseRequest(Double.class, request, AUCTION_BET_VALUE)
		.ifPresent(builder::bet);
	    parseRequest(Long.class, request, AUCTION_ID)
		.ifPresent(builder::auctionId);
	    parseRequest(Long.class, request, LOT_ID)
		.ifPresent(builder::lotId);
	    //all other fields are set via upper level
      } catch (NumberFormatException e) {
	    LOGGER.error("Failed to pars auction bet from request parameters");
	    throw new IllegalStateException(e);
      }
      return builder.build();
}

private static final SimpleDateFormat SIMPLE_DATE_FORMAT =
    new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

/**
 * Parses the auction information from the HttpServletRequest.
 *
 * @param request The HttpServletRequest object.
 * @return The parsed Auction object.
 */
public static Auction parseAuction(HttpServletRequest request) {
      var builder = Auction.builder();
      try {
	    parseRequest(Double.class, request, PRICE_STEP)
		.ifPresent(builder::priceStep);
	    LOGGER.trace("Date parsing: {}", request.getParameter(EVENT_DATE));
	    Date eventDate = SIMPLE_DATE_FORMAT.parse(request.getParameter(EVENT_DATE));
	    builder.eventDate(new java.sql.Date(eventDate.getTime()));
	    parseRequest(Long.class, request, AUCTION_TYPE_ID)
		.ifPresent(builder::auctionTypeId);
	    builder.duration(new Timestamp(120));
      } catch (NumberFormatException e) {
	    LOGGER.error("Failed to parse auction entity from request values by reason: {}", e.getMessage());
	    throw new IllegalStateException(e);
      } catch (ParseException e) {
	    LOGGER.error("Failed to parse date {}", request.getParameter(EVENT_DATE));
	    throw new IllegalStateException(e);
      }
      return builder.build();
}

public static <T extends Number> Optional<T> parseRequest(Class<T> clazz, HttpServletRequest request, String parameter) throws NumberFormatException {
      if (request.getParameter(parameter) == null) {
	    return Optional.empty();
      }
      return Optional.of(parse(clazz, request.getParameter(parameter)));
}

/**
 * Parses the request parameter to the specified data type.
 *
 * @param clazz The data type class.
 * @param value The value which should be parsed
 * @param <T>   The data type.
 * @return The parsed value as Optional.
 * @throws NumberFormatException if the parsing fails.
 */
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

/**
 * Parses the string value to the specified enum type.
 *
 * @param clazz The enum type class.
 * @param value The string value.
 * @param <T>   The enum type.
 * @return The parsed enum value as Optional.
 */
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
