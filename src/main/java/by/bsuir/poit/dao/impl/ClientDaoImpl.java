package by.bsuir.poit.dao.impl;

import by.bsuir.poit.bean.mappers.ClientMapper;
import by.bsuir.poit.context.Repository;
import by.bsuir.poit.dao.ClientDao;
import by.bsuir.poit.dao.connections.ConnectionPool;
import by.bsuir.poit.bean.Client;
import by.bsuir.poit.dao.exception.DataAccessException;
import by.bsuir.poit.dao.exception.DataModifyingException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@RequiredArgsConstructor
@Repository
public class ClientDaoImpl extends AbstractDao implements ClientDao {
private static final Logger LOGGER = LogManager.getLogger(ClientDaoImpl.class);
private final ConnectionPool pool;
private final ClientMapper clientMapper;

@Override
public Optional<Client> findById(long clientId) throws DataAccessException {
      Optional<Client> client;
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("select * from CLIENT where USER_ID = ?")) {
	    statement.setLong(1, clientId);
	    client = fetchEntityAndClose(statement, clientMapper);
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataAccessException(e);
      }
      return client;
}

@Override
public void save(Client client) {
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("insert into CLIENT " +
									 "(USER_ID, NAME, ACCOUNT, RANKING) " +
									 "values (?, ?, ?, ?)")) {
	    statement.setLong(1, client.getId());
	    statement.setString(2, client.getName());
	    statement.setDouble(3, client.getAccount());
	    statement.setDouble(4, client.getRanking());
	    if (statement.executeUpdate() != 1) {
		  final String msg = String.format("Failed to insert client with id=%d row into table", client.getId());
		  LOGGER.error(msg);
		  throw new DataModifyingException(msg);
	    }
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataAccessException(e);
      }
}
}
