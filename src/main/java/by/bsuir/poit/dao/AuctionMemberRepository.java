package by.bsuir.poit.dao;

import by.bsuir.poit.model.AuctionMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Paval Shlyk
 * @since 12/11/2023
 */
public interface AuctionMemberRepository extends JpaRepository<AuctionMember, Long> {
List<AuctionMember> findAllByAuctionId(long auctionId);

List<AuctionMember> findAllByClientId(long clientId);

List<AuctionMember> findAllByAuctionIdAndStatus(long auctionId, short status);
}
