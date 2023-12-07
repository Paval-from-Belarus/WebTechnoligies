package by.bsuir.poit.dao;

import by.bsuir.poit.model.AuctionBet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
public interface AuctionBetRepository extends JpaRepository<AuctionBet, Long> {
Optional<AuctionBet> findById(long id);

List<AuctionBet> findAllByAuctionId(long auctionId);

List<AuctionBet> findAllByAuctionIdAndClientId(long auctionId, long clientId);

List<AuctionBet> findAllByClientId(long clientId);
}
