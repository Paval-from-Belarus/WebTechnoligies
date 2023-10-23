package by.bsuir.poit.dao;

import by.bsuir.poit.connections.ConnectionPool;
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
public class AuctionDao {
private final ConnectionPool pool;
private final AuctionMapper mapper;
public Optional<Auction> findById(long id) {
	return Optional.empty();
}
public Optional<BlindAuction> findBlindById(long id) {
      return Optional.empty();
}
public Optional<BlitzAuction> findBlitzById(long id) {
      return Optional.empty();
}
public List<Auction> findAllByAuctionTypeIdAndAfterEventDate(long auctionTypeId, @NotNull Date start) {
	return List.of();
}
//using m2m table
public List<Auction> findAllByClientId(long clientId) {
      return List.of();
}
public List<Auction> findAllByAuctionTypeId(long auctionTypeId) {
      return List.of();
}
}
