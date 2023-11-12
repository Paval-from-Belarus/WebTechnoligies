package by.bsuir.poit.dao;

import by.bsuir.poit.bean.AuctionMember;

import java.util.List;

/**
 * @author Paval Shlyk
 * @since 12/11/2023
 */
public interface AuctionMemberDao {
      List<AuctionMember> findAllByAuctionId(long auctionId);
      List<AuctionMember> findAllByClientId(long clientId);
      List<AuctionMember> findAllByAuctionIdAndStatus(long auctionId, short status);
}
