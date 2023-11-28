package by.bsuir.poit.dao.impl;

import by.bsuir.poit.context.Repository;
import by.bsuir.poit.dao.AuctionTypeDao;
import by.bsuir.poit.dao.connections.ConnectionPool;
import by.bsuir.poit.dto.AuctionType;
import by.bsuir.poit.dto.mappers.AuctionTypeMapper;
import by.bsuir.poit.dao.exception.DataAccessException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@RequiredArgsConstructor
@Repository
public class AuctionTypeDaoImpl extends AbstractDao implements AuctionTypeDao {
private static final Logger LOGGER = LogManager.getLogger(AuctionTypeDaoImpl.class);
private final ConnectionPool pool;
private final AuctionTypeMapper mapper;

@Override
public Optional<AuctionType> findById(long typeId) {
      Optional<AuctionType> auctionType;
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("select * from AUCTION_TYPE where AUCTION_TYPE_ID = ?")) {
	    statement.setLong(1, typeId);
	    auctionType = fetchEntityAndClose(statement, mapper);
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataAccessException(e);
      }
      return auctionType;
}

@Override
public List<AuctionType> findAll() {
      List<AuctionType> list;
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("select * from AUCTION_TYPE")) {
	    list = fetchListAndClose(statement, mapper);
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataAccessException(e);
      }
      return list;


}
}
