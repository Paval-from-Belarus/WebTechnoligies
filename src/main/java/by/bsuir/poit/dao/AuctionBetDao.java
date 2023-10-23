package by.bsuir.poit.dao;

import by.bsuir.poit.connections.ConnectionPool;
import by.bsuir.poit.dao.entities.AuctionBet;
import by.bsuir.poit.dao.mappers.AuctionBetMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@RequiredArgsConstructor
public class AuctionBetDao {
private final ConnectionPool pool;
private final AuctionBetMapper mapper;
public Optional<AuctionBet> findById(long id) {
      return Optional.empty();
}

public List<AuctionBet> findAllByAuctionId(long auctionId) {
      return List.of();
}

public List<AuctionBet> findAllByAuctionIdAndClientId(long auctionId, long clientId) {
      return List.of();
}

public List<AuctionBet> findAllByClientId(long clientId) {
      return List.of();
}
}
