package by.bsuir.poit.dao.impl;

import by.bsuir.poit.context.Repository;
import by.bsuir.poit.dao.AuctionBetDao;
import by.bsuir.poit.dao.connections.ConnectionPool;
import by.bsuir.poit.bean.AuctionBet;
import by.bsuir.poit.bean.mappers.AuctionBetMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@RequiredArgsConstructor
@Repository
public class AuctionBetDaoImpl implements AuctionBetDao {
private final ConnectionPool pool;
private final AuctionBetMapper mapper;

@Override
public Optional<AuctionBet> findById(long id) {
      return Optional.empty();
}

@Override
public List<AuctionBet> findAllByAuctionId(long auctionId) {
      return null;
}

@Override
public List<AuctionBet> findAllByAuctionIdAndClientId(long auctionId, long clientId) {
      return null;
}

@Override
public List<AuctionBet> findAllByClientId(long clientId) {
      return null;
}
}
