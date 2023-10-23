package by.bsuir.poit.dao.impl;

import by.bsuir.poit.dao.DeliveryPointDao;
import by.bsuir.poit.dao.connections.ConnectionPool;
import by.bsuir.poit.dao.entities.DeliveryPoint;
import by.bsuir.poit.dao.mappers.DeliveryPointMapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@RequiredArgsConstructor
public class DeliveryPointDaoImpl implements DeliveryPointDao {
private final ConnectionPool pool;
private final DeliveryPointMapper mapper;

@Override
public Optional<DeliveryPoint> findById(long id) {
      return Optional.empty();
}
}
