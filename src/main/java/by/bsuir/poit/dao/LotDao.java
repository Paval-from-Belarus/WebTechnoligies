package by.bsuir.poit.dao;

import by.bsuir.poit.connections.ConnectionPool;
import by.bsuir.poit.dao.entities.EnglishLot;
import by.bsuir.poit.dao.entities.Lot;
import by.bsuir.poit.dao.mappers.LotMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@RequiredArgsConstructor
public class LotDao {
private final ConnectionPool pool;
private final LotMapper mapper;
public Optional<EnglishLot> findEnglishLotById(long id) {
      return Optional.empty();
}
public Optional<Lot> findById(long id) {
      return Optional.empty();
}
public List<Lot> findAllByAuctionId(long auctionId) {
      return List.of();
}
public List<Lot> findAllByClientId(long lotId) {
      return List.of();
}
public List<Lot> findAllByClientIdSortedByStartPriceDesc(long clientId) {
      return List.of();
}
}
