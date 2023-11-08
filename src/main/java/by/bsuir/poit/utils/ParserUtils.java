package by.bsuir.poit.utils;

import by.bsuir.poit.bean.Lot;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Paval Shlyk
 * @since 08/11/2023
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ParserUtils {
private static final Logger LOGGER = LogManager.getLogger(ParserUtils.class);
public static final String LOT_TITLE = "lot_title";
public static final String AUCTION_TYPE_ID = "auction_type_id";
public static final String SELLER_ID = "seller_id";
public static final String LOT_START_PRICE = "lot_start_price";

@Deprecated
public static Lot parseLot(HttpServletRequest request) {
      Lot lot;
      try {
	    lot = Lot.builder()
		      .title(request.getParameter(LOT_TITLE))
		      .auctionTypeId(Long.parseLong(request.getParameter(AUCTION_TYPE_ID)))
		      .status(Lot.BEFORE_AUCTION_STATUS)
		      .sellerId(Long.parseLong(request.getParameter(SELLER_ID)))
		      .startPrice(Double.parseDouble(request.getParameter(LOT_START_PRICE)))
		      .build();
      } catch (NumberFormatException e) {
	    LOGGER.error("Failed to parse lot from request parameters {}", e.toString());
	    throw new IllegalStateException(e);
      }
      return lot;
}

private static final Gson PARSER = new Gson();

public static <T> T parse(Class<T> clazz, HttpServletRequest request) throws IOException {
      T dto;
      try {
	    dto = PARSER.fromJson(request.getReader(), clazz);
      } catch (JsonSyntaxException e) {
	    LOGGER.error("Failed to parse dto from request");
	    throw new IllegalStateException(e);
      }
      return dto;
}

public static <T extends Enum<?>> Optional<T> parseEnum(Class<T> clazz, String value) {
      value = value.toUpperCase().replace("-", "_");
      for (T constant : clazz.getEnumConstants()) {
	    if (constant.name().equals(value)) {
		  return Optional.of(constant);
	    }
      }
      return Optional.empty();
}
}
