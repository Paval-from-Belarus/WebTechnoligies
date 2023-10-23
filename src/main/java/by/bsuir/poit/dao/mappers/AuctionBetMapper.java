package by.bsuir.poit.dao.mappers;

import by.bsuir.poit.dao.entities.AuctionBet;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@Mapper(unmappedSourcePolicy = ReportingPolicy.ERROR)
public interface AuctionBetMapper {
AuctionBetMapper INSTANCE = Mappers.getMapper(AuctionBetMapper.class);

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
