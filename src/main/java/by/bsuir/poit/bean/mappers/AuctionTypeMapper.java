package by.bsuir.poit.bean.mappers;

import by.bsuir.poit.bean.AuctionType;
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
public interface AuctionTypeMapper {
AuctionTypeMapper INSTANCE = Mappers.getMapper(AuctionTypeMapper.class);
default AuctionType fromResultSet(ResultSet set) throws SQLException {
      return AuctionType.builder()
		 .id(set.getLong("auction_type_id"))
		 .name(set.getString("name"))
		 .description(set.getString("description"))
		 .build();
}
}
