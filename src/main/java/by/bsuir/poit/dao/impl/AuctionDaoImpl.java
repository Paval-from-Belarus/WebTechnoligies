package by.bsuir.poit.dao.impl;

import by.bsuir.poit.dao.AuctionDao;
import by.bsuir.poit.dao.connections.ConnectionPool;
import by.bsuir.poit.dao.entities.Auction;
import by.bsuir.poit.dao.entities.BlindAuction;
import by.bsuir.poit.dao.entities.BlitzAuction;
import by.bsuir.poit.dao.mappers.AuctionMapper;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@RequiredArgsConstructor
public class AuctionDaoImpl implements AuctionDao {
private final ConnectionPool pool;
private final AuctionMapper mapper;

@Override
public Optional<Auction> findById(long id) {
      return Optional.empty();
}

@Override
public Optional<BlindAuction> findBlindById(long id) {
      return Optional.empty();
}

@Override
public Optional<BlitzAuction> findBlitzById(long id) {
      return Optional.empty();
}

@Override
public List<Auction> findAllByAuctionTypeIdAndAfterEventDate(long auctionTypeId, @NotNull Date start) {
      return null;
}

@Override
public List<Auction> findAllByClientId(long clientId) {
      return null;
}

@Override
public List<Auction> findAllByAuctionTypeId(long auctionTypeId) {
      return null;
}
}
