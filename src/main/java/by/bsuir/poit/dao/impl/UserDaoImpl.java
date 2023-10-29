package by.bsuir.poit.dao.impl;

import by.bsuir.poit.context.Repository;
import by.bsuir.poit.dao.UserDao;
import by.bsuir.poit.dao.connections.ConnectionPool;
import by.bsuir.poit.bean.User;
import by.bsuir.poit.bean.mappers.UserMapper;
import by.bsuir.poit.dao.exception.DataAccessException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 15/09/2023
 */
@RequiredArgsConstructor
@Repository
public class UserDaoImpl implements UserDao {
private final @NotNull ConnectionPool pool;
private final @NotNull UserMapper mapper;

@Override
public Optional<User> findById(@NotNull long id) throws DataAccessException {
      Optional<User> user = Optional.empty();
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("select * from COMMENT where ID= ?")) {
	    statement.setLong(1, id);
	    ResultSet set = statement.executeQuery();
	    if (set.next()) { //try to fetch the first
		  user = Optional.of(mapper.fromResultSet(set));
	    }
	    set.close();//we can do nothing if this method fails
      } catch (SQLException e) {
	    throw new DataAccessException(e);
      }
      return user;
}

@Override
public Optional<User> findByUserName(String name) {
      	return Optional.empty();
}

@Override
public boolean exists(String name) {
      return false;
}

@Override
public void setUserStatus(long userId, int status) {

}

@Override
public User save(User user) {
      return null;
}

@Override
public User update(User user) {
      return null;
}
}
