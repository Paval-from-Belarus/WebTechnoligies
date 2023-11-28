package by.bsuir.poit.dto.mappers;

import by.bsuir.poit.dto.AuctionType;
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
public interface AuctionTypeMapper extends ResultSetMapper<AuctionType> {
@Override
default AuctionType fromResultSet(ResultSet set) throws SQLException {
      return AuctionType.builder()
		 .id(set.getLong("auction_type_id"))
		 .name(set.getString("name"))
		 .description(set.getString("description"))
		 .build();
}
}
