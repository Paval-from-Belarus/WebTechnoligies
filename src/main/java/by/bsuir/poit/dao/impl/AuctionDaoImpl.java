package by.bsuir.poit.dao.impl;

import by.bsuir.poit.bean.AuctionType;
import by.bsuir.poit.context.Repository;
import by.bsuir.poit.dao.AuctionDao;
import by.bsuir.poit.dao.connections.ConnectionPool;
import by.bsuir.poit.bean.Auction;
import by.bsuir.poit.bean.BlindAuction;
import by.bsuir.poit.bean.BlitzAuction;
import by.bsuir.poit.bean.mappers.AuctionMapper;
import by.bsuir.poit.dao.exception.DataAccessException;
import by.bsuir.poit.dao.exception.DataModifyingException;
import by.bsuir.poit.services.exception.resources.ResourceModifyingException;
import by.bsuir.poit.utils.EntityUtils;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static by.bsuir.poit.utils.EntityUtils.*;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@RequiredArgsConstructor
@Repository
public class AuctionDaoImpl extends AbstractDao implements AuctionDao {
private static final Logger LOGGER = LogManager.getLogger(AuctionDaoImpl.class);
private final ConnectionPool pool;
private final AuctionMapper mapper;

@Override
public Optional<Auction> findById(long id) {
      Optional<Auction> auction;
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("select * from AUCTION where AUCTION_ID = ?")) {
	    statement.setLong(1, id);
	    auction = fetchEntityAndClose(statement, mapper);
	    if (auction.isPresent() && auction.get().getAuctionTypeId() == AuctionType.BLITZ) {
		  BlitzAuction blitzAuction = findBlitzById(id).orElseThrow(() -> newAuctionNotFoundException(id));
		  mapper.updateBlitzWithParent(blitzAuction, auction.get());
		  return Optional.of(blitzAuction);
	    }
	    if (auction.isPresent() && auction.get().getAuctionTypeId() == AuctionType.BLIND) {
		  BlindAuction blindAuction = findBlindById(id).orElseThrow(() -> newAuctionNotFoundException(id));
		  mapper.updateBlindWithParent(blindAuction, auction.get());
		  return Optional.of(blindAuction);
	    }
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataAccessException(e);
      }
      return auction;
}

@Override
public List<Auction> findAllAfterEventDate(Date date) {
      List<Auction> list;
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("select * from AUCTION where EVENT_DATE >= ?")) {
	    statement.setDate(1, toEntityDate(date));
	    list = fetchListAndClose(statement, mapper);
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataAccessException(e);
      }
      return list;
}


private Optional<BlindAuction> findBlindById(long id) {
      Optional<BlindAuction> auction;
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("select * from AUCTION_TYPE_BLIND where AUCTION_ID = ?")) {
	    statement.setLong(1, id);
	    auction = fetchEntityAndClose(statement, mapper::blindFromResultSet);
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataAccessException(e);
      }
      return auction;
}

private Optional<BlitzAuction> findBlitzById(long id) {
      Optional<BlitzAuction> auction;
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("select * from AUCTION_TYPE_BLITZ where AUCTION_ID = ?")) {
	    statement.setLong(1, id);
	    auction = fetchEntityAndClose(statement, mapper::blitzFromResultSet);
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataAccessException(e);
      }
      return auction;
}

@Override
public List<Auction> findAllByAuctionTypeIdAndAfterEventDate(long auctionTypeId, @NotNull Date start) {
      List<Auction> auctions;
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("select * from AUCTION where AUCTION_TYPE_ID = ? and EVENT_DATE >= ?")) {
	    statement.setLong(1, auctionTypeId);
	    statement.setDate(2, EntityUtils.toEntityDate(start));
	    auctions = fetchListAndClose(statement, mapper);
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataAccessException(e);
      }
      return auctions;
}

@Override
public List<Auction> findAllByAuctionTypeId(long auctionTypeId) {
      List<Auction> auctions;
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("select * from AUCTION where AUCTION_TYPE_ID = ?")) {
	    statement.setLong(1, auctionTypeId);
	    auctions = fetchListAndClose(statement, mapper);
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataAccessException(e);
      }
      return auctions;
}

@Override
public void save(Auction auction) throws ResourceModifyingException {
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("insert into AUCTION " +
									 "(EVENT_DATE, LAST_REGISTER_DATE, PRICE_STEP, AUCTION_TYPE_ID, DURATION, MEMBERS_LIMIT, ADMIN_USER_ID) " +
									 "VALUES(?, ?, ?, ?, ?, ?, ?)")) {
	    statement.setDate(1, auction.getEventDate());
	    statement.setDate(2, auction.getLastRegisterDate());
	    statement.setDouble(3, auction.getPriceStep());
	    statement.setLong(4, auction.getAuctionTypeId());
	    statement.setTimestamp(5, auction.getDuration());
	    statement.setInt(6, auction.getMembersLimit());
	    statement.setLong(7, auction.getAdminId());
	    if (statement.executeUpdate() != 1) {
		  final String msg = String.format("Failed to insert auction %s", auction);
		  LOGGER.error(msg);
		  throw new DataModifyingException(msg);
	    }
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataAccessException(e);
      }
}

private DataAccessException newAuctionNotFoundException(long auctionId) {
      final String msg = String.format("Failed to find auction or its derived by id=%d", auctionId);
      LOGGER.error(msg);
      return new DataAccessException(msg);
}
}
