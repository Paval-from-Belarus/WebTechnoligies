package by.bsuir.poit.dao.impl;

import by.bsuir.poit.dto.AuctionMemberDto;
import by.bsuir.poit.dto.mappers.AuctionMemberMapper;
import by.bsuir.poit.context.Repository;
import by.bsuir.poit.dao.AuctionMemberDao;
import by.bsuir.poit.dao.connections.ConnectionPool;
import by.bsuir.poit.dao.exception.DataAccessException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Paval Shlyk
 * @since 12/11/2023
 */
@Repository
@RequiredArgsConstructor
public class AuctionMemberDaoImpl extends AbstractDao implements AuctionMemberDao {
private static final Logger LOGGER = LogManager.getLogger(AuctionMemberDaoImpl.class);
private final ConnectionPool pool;
private final AuctionMemberMapper mapper;

@Override
public List<AuctionMemberDto> findAllByAuctionId(long auctionId) {
      List<AuctionMemberDto> list;
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("select * from auction_member where auction_id = ?")) {
	    statement.setLong(1, auctionId);
	    list = fetchListAndClose(statement, mapper);
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataAccessException(e);
      }
      return list;
}

@Override
public List<AuctionMemberDto> findAllByClientId(long clientId) {
      List<AuctionMemberDto> list;
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("select * from auction_member where client_id = ?")) {
	    statement.setLong(1, clientId);
	    list = fetchListAndClose(statement, mapper);
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataAccessException(e);
      }
      return list;
}

@Override
public List<AuctionMemberDto> findAllByAuctionIdAndStatus(long auctionId, short status) {
      List<AuctionMemberDto> list;
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("select * from auction_member where auction_id = ? and status = ?")) {
	    statement.setLong(1, auctionId);
	    statement.setShort(2, status);
	    list = fetchListAndClose(statement, mapper);
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataAccessException(e);
      }
      return list;
}

@Override
public void save(AuctionMemberDto member) {
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("insert into AUCTION_MEMBER " +
									 "(CLIENT_ID, AUCTION_ID, STATUS) " +
									 "VALUES (?, ?, ?)")) {
	    statement.setLong(1, member.getClientId());
	    statement.setLong(2, member.getAuctionId());
	    statement.setShort(3, member.getStatus());
	    if (statement.executeUpdate() != 1) {
		  final String msg = String.format("Failed to insert auction member: ", member);
		  LOGGER.error(msg);
		  throw new DataAccessException(msg);
	    }
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataAccessException(e);
      }
}
}
