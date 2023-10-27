package by.bsuir.poit.dao.impl;

import by.bsuir.poit.dao.LotDao;
import by.bsuir.poit.dao.connections.ConnectionPool;
import by.bsuir.poit.bean.EnglishLot;
import by.bsuir.poit.bean.Lot;
import by.bsuir.poit.bean.mappers.LotMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@RequiredArgsConstructor
public class LotDaoImpl implements LotDao {
private final ConnectionPool pool;
private final LotMapper mapper;

@Override
public Optional<EnglishLot> findEnglishLotById(long id) {
      return Optional.empty();
}

@Override
public Optional<Lot> findById(long id) {
      return Optional.empty();
}

@Override
public List<Lot> findAllByAuctionId(long auctionId) {
      return null;
}

@Override
public List<Lot> findAllByClientId(long lotId) {
      return null;
}

@Override
public List<Lot> findAllByClientIdSortedByStartPriceDesc(long clientId) {
      return null;
}
}
