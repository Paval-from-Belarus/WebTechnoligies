package by.bsuir.poit.dao.impl;

import by.bsuir.poit.dto.mappers.UserMapper;
import by.bsuir.poit.context.Repository;
import by.bsuir.poit.dao.UserDao;
import by.bsuir.poit.dao.connections.ConnectionPool;
import by.bsuir.poit.dto.UserDto;
import by.bsuir.poit.dao.exception.DataAccessException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 15/09/2023
 */
@RequiredArgsConstructor
@Repository
public class UserDaoImpl extends AbstractDao implements UserDao {
private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);
private final @NotNull ConnectionPool pool;
private final @NotNull UserMapper mapper;

@Override
public Optional<UserDto> findById(@NotNull long id) throws DataAccessException {
      Optional<UserDto> user;
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("select * from USER where USER_ID= ?")) {
	    statement.setLong(1, id);
	    user = fetchEntityAndClose(statement, mapper);
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataAccessException(e);
      }
      return user;
}

@Override
public Optional<UserDto> findByName(String name) {
      Optional<UserDto> user;
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("select * from USER where NAME= ?")) {
	    statement.setString(1, name);
	    user = fetchEntityAndClose(statement, mapper);
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataAccessException(e);
      }
      return user;
}

@Override
public boolean existsByName(String name) {
      boolean isExists;
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("select USER_ID from USER where NAME=?")) {
	    statement.setString(1, name);
	    isExists = checkEmptinessAndClose(statement);
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataAccessException(e);
      }
      return isExists;
}

@Override
public void setUserStatus(long userId, short status) {
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("update USER set STATUS=? where USER_ID=?")) {
	    statement.setShort(1, status);
	    statement.setLong(2, userId);
	    if (statement.executeUpdate() != 1) {
		  String msg = String.format("Failed to update user by given id=%s and status=%s", userId, status);
		  LOGGER.error(msg);
		  throw new DataAccessException(msg);
	    }
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataAccessException(e);
      }
}

@Override
public UserDto save(UserDto user) {
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("insert into USER " +
									 "(NAME, PHONE_NUMBER, EMAIL, ROLE, PASSWORD_HASH, SECURITY_SALT, STATUS) " +
									 "values (?, ?, ?, ?, ?, ?, ?)",
	       Statement.RETURN_GENERATED_KEYS)) {
	    statement.setString(1, user.getName());
	    statement.setString(2, user.getPhoneNumber());
	    statement.setString(3, user.getEmail());
	    statement.setShort(4, user.getRole());
	    statement.setString(5, user.getPasswordHash());
	    statement.setString(6, user.getSecuritySalt());
	    statement.setShort(7, user.getStatus());
	    if (statement.executeUpdate() != 1) {
		  final String msg = String.format("Failed to insert new user %s", user);
		  LOGGER.error(msg);
		  throw new DataAccessException(msg);
	    }
	    Long userId = fetchLongKeyAndClose(statement).orElseThrow(() -> {
		  final String msg = "Failed to fetch generated user id";
		  LOGGER.error(msg);
		  return new DataAccessException(msg);
	    });
	    user.setId(userId);
      } catch (SQLException e) {
	    LOGGER.error("message {} and stack-trace {}", e.toString(), Arrays.toString(e.getStackTrace()));
	    throw new DataAccessException(e);
      }
      return user;
}
}
