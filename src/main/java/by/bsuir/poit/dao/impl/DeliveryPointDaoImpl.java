package by.bsuir.poit.dao.impl;

import by.bsuir.poit.context.Repository;
import by.bsuir.poit.dao.DeliveryPointDao;
import by.bsuir.poit.dao.connections.ConnectionPool;
import by.bsuir.poit.dto.DeliveryPointDto;
import by.bsuir.poit.dto.mappers.DeliveryPointMapper;
import by.bsuir.poit.dao.exception.DataAccessException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;

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
public class DeliveryPointDaoImpl extends AbstractDao implements DeliveryPointDao {
private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(DeliveryPointDaoImpl.class);
private final ConnectionPool pool;
private final DeliveryPointMapper mapper;

@Override
public Optional<DeliveryPointDto> findById(long id) {
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("select * from DELIVERY_POINT where DELIVERY_POINT_ID = ?")) {
	    statement.setLong(1, id);
	    return fetchEntityAndClose(statement, mapper);
      } catch (SQLException e) {
	    LOGGER.error(e);
	    throw new DataAccessException(e);
      }
}
}
