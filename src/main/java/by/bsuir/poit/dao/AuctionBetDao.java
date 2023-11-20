package by.bsuir.poit.dao;

import by.bsuir.poit.bean.AuctionBet;

import java.util.List;
import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
public interface AuctionBetDao {
Optional<AuctionBet> findById(long id);

List<AuctionBet> findAllByAuctionId(long auctionId);

List<AuctionBet> findAllByAuctionIdAndClientId(long auctionId, long clientId);

List<AuctionBet> findAllByClientId(long clientId);

void save(AuctionBet bet);
}
