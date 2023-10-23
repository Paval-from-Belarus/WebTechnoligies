package by.bsuir.poit.dao;

import by.bsuir.poit.connections.ConnectionPool;
import by.bsuir.poit.dao.entities.DeliveryPoint;
import by.bsuir.poit.dao.mappers.DeliveryPointMapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@RequiredArgsConstructor
public class DeliveryPointDao {
private final ConnectionPool pool;
private final DeliveryPointMapper mapper;
public Optional<DeliveryPoint> findById(long id) {
      return Optional.empty();
}
}
