package by.bsuir.poit.dao.impl;

import by.bsuir.poit.context.Repository;
import by.bsuir.poit.dao.ClientFeedbackDao;
import by.bsuir.poit.dao.connections.ConnectionPool;
import by.bsuir.poit.dto.ClientFeedback;
import by.bsuir.poit.dto.mappers.ClientFeedbackMapper;
import by.bsuir.poit.dao.exception.DataAccessException;
import by.bsuir.poit.dao.exception.DataModifyingException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@RequiredArgsConstructor
@Repository
public class ClientFeedbackDaoImpl extends AbstractDao implements ClientFeedbackDao {
private static final Logger LOGGER = LogManager.getLogger(ClientFeedbackDaoImpl.class);
private final ConnectionPool pool;
private final ClientFeedbackMapper mapper;

@Override
public List<ClientFeedback> findAllBySellerId(long clientId) {
      List<ClientFeedback> list;
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("select * from CLIENT_FEEDBACK where CLIENT_TARGET_ID = ?")) {
	    statement.setLong(1, clientId);
	    list = fetchListAndClose(statement, mapper);
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataAccessException(e);
      }
      return list;
}

@Override
public List<ClientFeedback> findAllByCustomerId(long clientId) {
      List<ClientFeedback> list;
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("select * from CLIENT_FEEDBACK where CLIENT_AUTHOR_ID = ?")) {
	    statement.setLong(1, clientId);
	    list = fetchListAndClose(statement, mapper);
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataAccessException(e);
      }
      return list;
}

@Override
public List<ClientFeedback> findAllByLotId(long lotId) {
      List<ClientFeedback> list;
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("select * from CLIENT_FEEDBACK where LOT_ID = ?")) {
	    statement.setLong(1, lotId);
	    list = fetchListAndClose(statement, mapper);
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataAccessException(e);
      }
      return list;
}

@Override
public Optional<ClientFeedback> findByIdAndAuthorId(long lotId, long authorId) {
      Optional<ClientFeedback> feedback;
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("select * from CLIENT_FEEDBACK where LOT_ID = ? and CLIENT_AUTHOR_ID = ?")) {
	    statement.setLong(1, lotId);
	    statement.setLong(2, authorId);
	    feedback = fetchEntityAndClose(statement, mapper);
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataAccessException(e);
      }
      return feedback;

}

@Override
public Optional<ClientFeedback> findByIdAndTargetId(long lotId, long targetId) {
      Optional<ClientFeedback> feedback;
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("select * from CLIENT_FEEDBACK where LOT_ID = ? and CLIENT_TARGET_ID = ?")) {
	    statement.setLong(1, lotId);
	    statement.setLong(2, targetId);
	    feedback = fetchEntityAndClose(statement, mapper);
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataAccessException(e);
      }
      return feedback;
}


@Override
public List<ClientFeedback> findAllBySellerIdSortedByRankingDesc(long clientId) {
      List<ClientFeedback> list;
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("select * from CLIENT_FEEDBACK where CLIENT_TARGET_ID = ? ORDER BY RANKING DESC")) {
	    statement.setLong(1, clientId);
	    list = fetchListAndClose(statement, mapper);
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataAccessException(e);
      }
      return list;
}

@Override
public List<ClientFeedback> findAllByCustomerIdSortedByRankingDesc(long clientId) {
      List<ClientFeedback> list;
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("select * from CLIENT_FEEDBACK where CLIENT_AUTHOR_ID = ? ORDER BY RANKING DESC")) {
	    statement.setLong(1, clientId);
	    list = fetchListAndClose(statement, mapper);
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataAccessException(e);
      }
      return list;
}

@Override
public ClientFeedback save(ClientFeedback entity) {
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("insert into CLIENT_FEEDBACK " +
									 "(RANKING, TEXT, CLIENT_AUTHOR_ID, CLIENT_TARGET_ID) " +
									 "VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
	    statement.setDouble(1, entity.getRanking());
	    statement.setString(2, entity.getText());
	    statement.setLong(3, entity.getAuthorId());
	    statement.setLong(4, entity.getTargetId());
	    if (statement.executeUpdate() != 1) {
		  final String msg = String.format("Failed to insert Client feedback %s", entity);
		  LOGGER.error(msg);
		  throw new DataModifyingException(msg);
	    }
	    long feedbackId = fetchLongKeyAndClose(statement).orElseThrow(() -> {
		  final String msg = "Failed to fetched generated client feedback id";
		  LOGGER.error(msg);
		  return new DataAccessException(msg);
	    });
	    entity.setId(feedbackId);
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataModifyingException(e);
      }
      return entity;
}
}
