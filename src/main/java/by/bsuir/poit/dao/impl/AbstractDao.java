package by.bsuir.poit.dao.impl;

import by.bsuir.poit.dto.mappers.ResultSetMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 08/11/2023
 */
public abstract class AbstractDao {
/**
 * Fetch entity and close result set
 *
 * @param statement
 * @return fetched entity
 */
protected <T> Optional<T> fetchEntityAndClose(PreparedStatement statement, ResultSetMapper<T> mapper) throws SQLException {
      Optional<T> optional = Optional.empty();
      try (ResultSet set = statement.executeQuery()) {
	    if (set.next()) {
		  optional = Optional.of(mapper.fromResultSet(set));
	    }
      }
      return optional;
}
protected <T> List<T> fetchListAndClose(PreparedStatement statement, ResultSetMapper<T> mapper) throws SQLException {
      List<T> list = new ArrayList<>();
      try (ResultSet set = statement.executeQuery()) {
	    while (set.next()) {
		  list.add(mapper.fromResultSet(set));
	    }
      }
      return list;
}

/**
 * @param statement
 * @return true if a set is not empty. Otherwise, return false
 * @throws SQLException
 */
protected boolean checkEmptinessAndClose(PreparedStatement statement) throws SQLException {
      boolean hasData;
      try (ResultSet set = statement.executeQuery()) {
	    hasData = set.next();
      }
      return hasData;
}

protected Optional<Long> fetchLongKeyAndClose(Statement statement) throws SQLException {
      Optional<Long> key = Optional.empty();
      try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
	    if (generatedKeys.next()) {
		  key = Optional.of(generatedKeys.getLong(1));
	    }
      }
      return key;
}
}
