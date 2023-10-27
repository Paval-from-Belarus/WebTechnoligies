package by.bsuir.poit.bean.mappers;

import by.bsuir.poit.bean.DeliveryPoint;
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
public interface DeliveryPointMapper {
DeliveryPointMapper INSTANCE = Mappers.getMapper(DeliveryPointMapper.class);
default DeliveryPoint fromResultSet(ResultSet set) throws SQLException {
      return DeliveryPoint.builder()
		 .id(set.getLong("delivery_point_id"))
		 .cityCode(set.getString("city_code"))
		 .streetName(set.getString("street_name"))
		 .houseNumber(set.getString("house_number"))
		 .name(set.getString("delivery_point_name"))
		 .build();
}
}
