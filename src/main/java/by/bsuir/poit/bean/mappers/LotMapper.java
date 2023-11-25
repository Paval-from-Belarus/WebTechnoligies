package by.bsuir.poit.bean.mappers;

import by.bsuir.poit.bean.EnglishLot;
import by.bsuir.poit.bean.Lot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@Mapper(unmappedSourcePolicy = ReportingPolicy.ERROR, componentModel = MappingConstants.ComponentModel.JAKARTA)
public interface LotMapper extends ResultSetMapper<Lot> {

EnglishLot updateEnglishWithParent(@MappingTarget EnglishLot englishLot, Lot lot);

@Override
default Lot fromResultSet(ResultSet set) throws SQLException {
      return Lot.builder()
		 .id(set.getLong("lot_id"))
		 .title(set.getString("title"))
		 .status(set.getShort("status"))
		 .startPrice(set.getDouble("start_price"))
		 .actualPrice(set.getObject("actual_price", Double.class))
		 .auctionTypeId(set.getLong("auction_type_id"))
		 .sellerId(set.getLong("client_seller_id"))
		 .customerId(set.getObject("client_customer_id", Long.class))
		 .deliveryPointId(set.getObject("delivery_point_id", Long.class))
		 .auctionId(set.getObject("auction_id", Long.class))
		 .build();
}

//the request holds all information about english lot
default EnglishLot fromResultSetEnglish(ResultSet set) throws SQLException {
      return EnglishLot.builder()
		 .redemptionPrice(set.getDouble("redemption_price"))
		 .build();
}
}
