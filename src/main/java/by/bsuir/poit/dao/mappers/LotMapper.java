package by.bsuir.poit.dao.mappers;

import by.bsuir.poit.dao.entities.EnglishLot;
import by.bsuir.poit.dao.entities.Lot;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@Mapper(unmappedSourcePolicy = ReportingPolicy.ERROR)
public interface LotMapper {
LotMapper INSTANCE = Mappers.getMapper(LotMapper.class);
EnglishLot updateEnglishWithParent(@MappingTarget EnglishLot englishLot, Lot lot);
default Lot fromResultSet(ResultSet set) throws SQLException {
      return Lot.builder()
		 .id(set.getLong("lot_id"))
		 .title(set.getString("title"))
		 .status(set.getInt("status"))
		 .startPrice(set.getDouble("start_price"))
		 .actualPrice(set.getDouble("actual_price"))
		 .auctionTypeId(set.getLong("auction_type_id"))
		 .sellerId(set.getLong("client_seller_id"))
		 .customerId(set.getLong("client_customer_id"))
		 .deliveryPointId(set.getLong("delivery_point_id"))
		 .auctionId(set.getLong("auction_id"))
		 .build();
}

//the request holds all information about english lot
default EnglishLot fromResultSetEnglish(ResultSet set) throws SQLException {
      Lot lot = fromResultSet(set);
      EnglishLot englishLot = EnglishLot.builder()
				       .redemptionPrice(set.getDouble("redemption_price"))
				       .build();
      return updateEnglishWithParent(englishLot, lot);
}
}
