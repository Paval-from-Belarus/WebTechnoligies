package by.bsuir.poit.bean.mappers;

import by.bsuir.poit.bean.Client;
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
public interface ClientMapper {
default Client clientFromUserSet(ResultSet set) throws SQLException {
      return Client.builder()
		 .id(set.getLong("user_id"))
		 .account(set.getDouble("account"))
		 .ranking(set.getDouble("ranking"))
		 .status(set.getInt("status"))
		 .build();
}
}
