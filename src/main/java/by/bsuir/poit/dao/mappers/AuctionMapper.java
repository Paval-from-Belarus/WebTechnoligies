package by.bsuir.poit.dao.mappers;

import by.bsuir.poit.dao.entities.Auction;
import org.mapstruct.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@Mapper
public interface AuctionMapper {
default Auction fromResultSet(ResultSet set) throws SQLException {
      return Auction.builder()
		 .id(set.getLong("auction_id"))
		 .eventDate(set.getDate("event_date"))
		 .lastRegisterDate(set.getDate("last_register_date"))
		 .priceStep(set.getDouble("price_step"))
		 .auctionTypeId(set.getLong("type_id"))
		 .duration(set.getTimestamp("duration"))
		 .membersLimit(set.getInt("members_limit"))
		 .build();
}
}
