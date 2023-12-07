package by.bsuir.poit.dao.impl;

import by.bsuir.poit.dto.AuctionTypeDto;
import by.bsuir.poit.context.Repository;
import by.bsuir.poit.dao.AuctionDao;
import by.bsuir.poit.dao.connections.ConnectionPool;
import by.bsuir.poit.dto.AuctionDto;
import by.bsuir.poit.dto.BlindAuctionDto;
import by.bsuir.poit.dto.BlitzAuctionDto;
import by.bsuir.poit.dto.mappers.AuctionMapper;
import by.bsuir.poit.dao.exception.DataAccessException;
import by.bsuir.poit.dao.exception.DataModifyingException;
import by.bsuir.poit.services.exception.resources.ResourceModifyingException;
import by.bsuir.poit.utils.EntityUtils;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
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
public Optional<AuctionDto> findById(long id) {
      Optional<AuctionDto> auction;
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("select * from AUCTION where AUCTION_ID = ?")) {
	    statement.setLong(1, id);
	    auction = fetchEntityAndClose(statement, mapper);
	    if (auction.isPresent() && auction.get().getAuctionTypeId() == AuctionTypeDto.BLITZ) {
		  BlitzAuctionDto blitzAuction = findBlitzById(id).orElseThrow(() -> newAuctionNotFoundException(id));
		  mapper.updateBlitzWithParent(blitzAuction, auction.get());
		  return Optional.of(blitzAuction);
	    }
	    if (auction.isPresent() && auction.get().getAuctionTypeId() == AuctionTypeDto.BLIND) {
		  BlindAuctionDto blindAuction = findBlindById(id).orElseThrow(() -> newAuctionNotFoundException(id));
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
public List<AuctionDto> findAllAfterEventDate(Date date) {
      List<AuctionDto> list;
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


private Optional<BlindAuctionDto> findBlindById(long id) {
      Optional<BlindAuctionDto> auction;
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

private Optional<BlitzAuctionDto> findBlitzById(long id) {
      Optional<BlitzAuctionDto> auction;
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
public List<AuctionDto> findAllByAuctionTypeIdAndAfterEventDate(long auctionTypeId, @NotNull Date start) {
      List<AuctionDto> auctions;
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
public List<AuctionDto> findAllByAuctionTypeId(long auctionTypeId) {
      List<AuctionDto> auctions;
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
public List<AuctionDto> findHeadersAllByAdminIdSortedByEventDateDesc(long adminId) {
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("select * from AUCTION " +
									 "where ADMIN_USER_ID = ?" +
									 "order by EVENT_DATE DESC ")) {
	    statement.setLong(1, adminId);
	    return fetchListAndClose(statement, mapper);
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataAccessException(e);
      }


}

@Override
//@Transactional
public void save(AuctionDto auction) throws ResourceModifyingException {
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("insert into AUCTION " +
									 "(EVENT_DATE, LAST_REGISTER_DATE, PRICE_STEP, AUCTION_TYPE_ID, DURATION, MEMBERS_LIMIT, ADMIN_USER_ID) " +
									 "VALUES(?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
	    statement.setDate(1, auction.getEventDate());
	    statement.setNull(2, Types.DATE);
//	    statement.setDate(2, auction.getLastRegisterDate());
	    statement.setDouble(3, auction.getPriceStep());
	    statement.setLong(4, auction.getAuctionTypeId());
	    statement.setNull(5, Types.TIMESTAMP);
//	    statement.setTimestamp(5, auction.getDuration());
	    statement.setNull(6, Types.INTEGER);
//	    statement.setInt(6, auction.getMembersLimit());
	    statement.setLong(7, auction.getAdminId());
	    if (statement.executeUpdate() != 1) {
		  final String msg = String.format("Failed to insert auction %s", auction);
		  LOGGER.error(msg);
		  throw new DataModifyingException(msg);
	    }
	    Optional<Long> auctionId = fetchLongKeyAndClose(statement);
	    if (auctionId.isEmpty()) {
		  throw new IllegalStateException("The Sql Exception should be thrown before");
	    }
	    auction.setId(auctionId.get());
	    if (auction.getAuctionTypeId() == AuctionTypeDto.BLITZ) {
		  BlitzAuctionDto blitzAuction = BlitzAuctionDto.builder()
						  .iterationLimit(new Timestamp(120))
						  .memberExcludeLimit(5)
						  .build();
		  blitzAuction = mapper.updateBlitzWithParent(blitzAuction, auction);
		  saveBlitz(blitzAuction);
	    }
	    if (auction.getAuctionTypeId() == AuctionTypeDto.BLIND) {
		  BlindAuctionDto blindAuction = BlindAuctionDto.builder()
						  .betLimit(2)
						  .timeout(new Timestamp(12))
						  .build();
		  blindAuction = mapper.updateBlindWithParent(blindAuction, auction);
		  saveBlind(blindAuction);
	    }
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataAccessException(e);
      }
}

private void saveBlitz(BlitzAuctionDto auction) {
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("insert into AUCTION_TYPE_BLITZ " +
									 "(AUCTION_ID, ITERATION_TIME, MEMBERS_EXCLUDE_LIMIT) " +
									 "values (?, ?, ?)")) {
	    statement.setLong(1, auction.getId());
	    statement.setTimestamp(2, auction.getIterationLimit());
	    statement.setInt(3, auction.getMemberExcludeLimit());
	    if (statement.executeUpdate() != 1) {
		  final String msg = String.format("Failed to insert blitz auction %s", auction);
		  LOGGER.error(msg);
		  throw new DataModifyingException(msg);
	    }
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataAccessException(e);
      }
}

private void saveBlind(BlindAuctionDto auction) {
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("insert into AUCTION_TYPE_BLIND " +
									 "(AUCTION_ID, BET_LIMIT, TIMEOUT) " +
									 "values (?, ?, ?)")) {
	    statement.setLong(1, auction.getId());
	    statement.setInt(2, auction.getBetLimit());
	    statement.setTimestamp(3, auction.getTimeout());
	    if (statement.executeUpdate() != 1) {
		  final String msg = String.format("Failed to insert blind auction %s", auction);
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
