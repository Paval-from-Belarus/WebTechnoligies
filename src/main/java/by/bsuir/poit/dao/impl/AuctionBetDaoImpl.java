package by.bsuir.poit.dao.impl;

import by.bsuir.poit.context.Repository;
import by.bsuir.poit.dao.AuctionBetDao;
import by.bsuir.poit.dao.connections.ConnectionPool;
import by.bsuir.poit.dto.AuctionBetDto;
import by.bsuir.poit.dto.mappers.AuctionBetMapper;
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
public class AuctionBetDaoImpl extends AbstractDao implements AuctionBetDao {
private static final Logger LOGGER = LogManager.getLogger(AuctionBetDaoImpl.class);
private final ConnectionPool pool;
private final AuctionBetMapper mapper;

@Override
public Optional<AuctionBetDto> findById(long id) {
      Optional<AuctionBetDto> auctionBet;
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("select * from AUCTION_BET where AUCTION_BET_ID = ?")) {
	    statement.setLong(1, id);
	    auctionBet = fetchEntityAndClose(statement, mapper);
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataAccessException(e);
      }
      return auctionBet;
}

@Override
public List<AuctionBetDto> findAllByAuctionId(long auctionId) {
      List<AuctionBetDto> betList;
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("select * from AUCTION_BET where AUCTION_ID = ?")) {
	    statement.setLong(1, auctionId);
	    betList = fetchListAndClose(statement, mapper);
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataAccessException(e);
      }
      return betList;
}

@Override
public List<AuctionBetDto> findAllByAuctionIdAndClientId(long auctionId, long clientId) {
      List<AuctionBetDto> list;
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("select * from AUCTION_BET where AUCTION_ID = ? and CLIENT_ID = ?")) {
	    statement.setLong(1, auctionId);
	    statement.setLong(2, clientId);
	    list = fetchListAndClose(statement, mapper);
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataAccessException(e);
      }
      return list;
}

@Override
public List<AuctionBetDto> findAllByClientId(long clientId) {
      List<AuctionBetDto> list;
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("select * from AUCTION_BET where CLIENT_ID = ?")) {
	    statement.setLong(1, clientId);
	    list = fetchListAndClose(statement, mapper);
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataAccessException(e);
      }
      return list;
}

@Override
public void save(AuctionBetDto bet) {
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("insert into AUCTION_BET " +
									 "(BET, TIME, LOT_ID, CLIENT_ID, AUCTION_ID) " +
									 "values (?, ?, ?, ?, ?)",
	       Statement.RETURN_GENERATED_KEYS)) {
	    statement.setDouble(1, bet.getBet());
	    statement.setTimestamp(2, bet.getTime());
	    statement.setLong(3, bet.getLotId());
	    statement.setLong(4, bet.getClientId());
	    statement.setLong(5, bet.getAuctionId());
	    if (statement.executeUpdate() != 1) {
		  final String msg = String.format("Failed to insert new bet %s", bet);
		  LOGGER.error(msg);
		  throw new DataModifyingException(msg);
	    }
	    //even no fetching because user is not interesting auction
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataAccessException(e);
      }
}
}
