package by.bsuir.poit.dao.impl;

import by.bsuir.poit.bean.AuctionMember;
import by.bsuir.poit.bean.mappers.AuctionMemberMapper;
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
public class AuctionMemberDaoImpl extends AbstractDao<AuctionMember> implements AuctionMemberDao {
private static final Logger LOGGER = LogManager.getLogger(AuctionMemberDaoImpl.class);
private final ConnectionPool pool;
private final AuctionMemberMapper mapper;

@Override
public List<AuctionMember> findAllByAuctionId(long auctionId) {
      List<AuctionMember> list;
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
public List<AuctionMember> findAllByClientId(long clientId) {
      List<AuctionMember> list;
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
public List<AuctionMember> findAllByAuctionIdAndStatus(long auctionId, short status) {
      List<AuctionMember> list;
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("select * from auction_membere where auction_id = ? and status = ?")) {
	    statement.setLong(1, auctionId);
	    statement.setShort(2, status);
	    list = fetchListAndClose(statement, mapper);
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataAccessException(e);
      }
      return list;
}
}
