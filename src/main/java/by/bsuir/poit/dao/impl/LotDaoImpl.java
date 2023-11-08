package by.bsuir.poit.dao.impl;

import by.bsuir.poit.context.Repository;
import by.bsuir.poit.dao.LotDao;
import by.bsuir.poit.dao.connections.ConnectionPool;
import by.bsuir.poit.bean.EnglishLot;
import by.bsuir.poit.bean.Lot;
import by.bsuir.poit.bean.mappers.LotMapper;
import by.bsuir.poit.dao.exception.DataAccessException;
import by.bsuir.poit.dao.exception.DataModifyingException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@RequiredArgsConstructor
@Repository
public class LotDaoImpl extends AbstractDao<Lot> implements LotDao {
private static final Logger LOGGER = LogManager.getLogger(LotDaoImpl.class);
private final ConnectionPool pool;
private final LotMapper mapper;

@Override
public Optional<EnglishLot> findEnglishLotById(long id) {
      Optional<EnglishLot> englishLot = Optional.empty();
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("select * from AUCTION_TYPE_ENGLISH_LOT where LOT_ID = ?")) {
	    statement.setLong(1, id);
	    Optional<Lot> lot = fetchEntityAndClose(statement, mapper::fromResultSetEnglish);
	    if (lot.isPresent()) {
		  englishLot = Optional.of((EnglishLot) lot.get());
	    }
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataAccessException(e);
      }
      return englishLot;
}

@Override
public Optional<Lot> findById(long id) {
      Optional<Lot> lot;
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("select * from LOT where LOT_ID = ?")) {
	    statement.setLong(1, id);
	    lot = fetchEntityAndClose(statement, mapper);
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataAccessException(e);
      }
      return lot;
}

@Override
public List<Lot> findAllByAuctionId(long auctionId) {
      List<Lot> lots;
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("select * from LOT where AUCTION_ID = ?")) {
	    statement.setLong(1, auctionId);
	    lots = fetchListAndClose(statement, mapper);
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataAccessException(e);
      }
      return lots;
}

@Override
public List<Lot> findAllBySellerId(long sellerId) {
      List<Lot> lots;
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("select * from LOT where CLIENT_SELLER_ID = ?")) {
	    statement.setLong(1, sellerId);
	    lots = fetchListAndClose(statement, mapper);
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataAccessException(e);
      }
      return lots;
}

@Override
public List<Lot> findAllByCustomerId(long customerId) {
      List<Lot> lots;
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("select * from LOT where CLIENT_CUSTOMER_ID = ?")) {
	    statement.setLong(1, customerId);
	    lots = fetchListAndClose(statement, mapper);
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataAccessException(e);
      }
      return lots;
}

@Override
public Lot save(Lot lot) throws DataModifyingException {
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("insert into LOT " +
									 "(TITLE, START_PRICE, ACTUAL_PRICE, AUCTION_TYPE_ID, STATUS, CLIENT_SELLER_ID, CLIENT_CUSTOMER_ID, DELIVERY_POINT_ID, AUCTION_ID) " +
									 "values (?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
	    statement.setString(1, lot.getTitle());
	    statement.setDouble(2, lot.getStartPrice());
	    statement.setDouble(3, lot.getActualPrice());
	    statement.setLong(4, lot.getAuctionTypeId());
	    statement.setShort(5, lot.getStatus());
	    statement.setLong(6, lot.getSellerId());
	    statement.setLong(7, lot.getCustomerId());
	    statement.setLong(8, lot.getDeliveryPointId());
	    statement.setLong(9, lot.getAuctionId());
	    if (statement.executeUpdate() != 1) {
		  final String msg = String.format("Failed to insert lot entity into table: %s", lot);
		  LOGGER.error(msg);
		  throw new DataModifyingException(msg);
	    }
	    Long lotId = fetchLongKeyAndClose(statement).orElseThrow(() -> {
		  final String msg = String.format("Failed to fetch generated keys from lot table: %s", lot);
		  LOGGER.error(msg);
		  return new DataModifyingException(msg);
	    });
	    lot.setId(lotId);
      } catch (SQLException e) {
	    LOGGER.error("Failed to insert new lot message {} and stack-trace {}", e.toString(), Arrays.toString(e.getStackTrace()));
	    throw new DataAccessException(e);
      }
      return lot;

}
}
