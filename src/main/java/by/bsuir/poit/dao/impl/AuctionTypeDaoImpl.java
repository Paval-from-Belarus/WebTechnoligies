package by.bsuir.poit.dao.impl;

import by.bsuir.poit.dao.AuctionTypeDao;
import by.bsuir.poit.dao.connections.ConnectionPool;
import by.bsuir.poit.dao.entities.AuctionType;
import by.bsuir.poit.dao.mappers.AuctionTypeMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@RequiredArgsConstructor
public class AuctionTypeDaoImpl implements AuctionTypeDao {
private final ConnectionPool pool;
private final AuctionTypeMapper mapper;

@Override
public Optional<AuctionType> findById(long typeId) {
      return Optional.empty();
}

@Override
public List<AuctionType> findAll() {
      return null;
}
}
