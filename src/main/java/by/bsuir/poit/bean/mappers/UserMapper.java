package by.bsuir.poit.bean.mappers;

import by.bsuir.poit.bean.User;
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
public interface UserMapper {
default User fromResultSet(ResultSet set) throws SQLException {
      return User.builder()
		 .id(set.getLong("user_id"))
		 .name(set.getString("name"))
		 .phoneNumber(set.getString("phone_number"))
		 .email(set.getString("email"))
		 .role(set.getInt("role"))
		 .passwordHash(set.getString("password_hash"))
		 .securitySalt(set.getString("security_salt"))
		 .build();
}
}
