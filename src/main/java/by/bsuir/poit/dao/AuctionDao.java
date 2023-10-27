package by.bsuir.poit.dao;

import by.bsuir.poit.bean.Auction;
import by.bsuir.poit.bean.BlindAuction;
import by.bsuir.poit.bean.BlitzAuction;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
public interface AuctionDao {
Optional<Auction> findById(long id);

Optional<BlindAuction> findBlindById(long id);

Optional<BlitzAuction> findBlitzById(long id);

List<Auction> findAllByAuctionTypeIdAndAfterEventDate(long auctionTypeId, @NotNull Date start);

//using m2m table
List<Auction> findAllByClientId(long clientId);

List<Auction> findAllByAuctionTypeId(long auctionTypeId);
}
