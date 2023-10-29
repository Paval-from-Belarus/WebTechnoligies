package by.bsuir.poit.bean.mappers;

import by.bsuir.poit.bean.AuctionBet;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@Mapper(unmappedSourcePolicy = ReportingPolicy.ERROR, componentModel = MappingConstants.ComponentModel.JAKARTA)
public interface AuctionBetMapper {

default AuctionBet fromResultSet(ResultSet set) throws SQLException {
      return AuctionBet.builder()
		 .id(set.getLong("auction_bet_id"))
		 .bet(set.getDouble("bet"))
		 .time(set.getTimestamp("time"))
		 .lotId(set.getLong("lot_id"))
		 .clientId(set.getLong("client_id"))
		 .auctionId(set.getLong("auction_id"))
		 .build();
}
}
