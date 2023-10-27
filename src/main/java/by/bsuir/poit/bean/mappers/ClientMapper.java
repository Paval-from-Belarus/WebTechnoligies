package by.bsuir.poit.bean.mappers;

import by.bsuir.poit.bean.Client;
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
public interface ClientMapper {
ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);
default Client clientFromUserSet(ResultSet set) throws SQLException {
      return Client.builder()
		 .id(set.getLong("user_id"))
		 .account(set.getDouble("account"))
		 .ranking(set.getDouble("ranking"))
		 .status(set.getInt("status"))
		 .build();
}
}
