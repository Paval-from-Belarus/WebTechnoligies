package by.bsuir.poit.dto.mappers;

import by.bsuir.poit.dto.User;
import org.mapstruct.*;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, componentModel = MappingConstants.ComponentModel.JAKARTA)
public interface UserMapper extends ResultSetMapper<User> {
@Override
default User fromResultSet(ResultSet set) throws SQLException {
      return User.builder()
		 .id(set.getLong("user_id"))
		 .name(set.getString("name"))
		 .phoneNumber(set.getString("phone_number"))
		 .email(set.getString("email"))
		 .role(set.getShort("role"))
		 .status(set.getShort("status"))
		 .passwordHash(set.getString("password_hash"))
		 .securitySalt(set.getString("security_salt"))
		 .build();
}
}
