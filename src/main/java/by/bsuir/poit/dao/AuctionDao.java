package by.bsuir.poit.dao;

import by.bsuir.poit.dto.Auction;
import by.bsuir.poit.services.exception.resources.ResourceModifyingException;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
public interface AuctionDao {
/**
 * @param id the id of auction
 * @return instance Auction or its corresponding inherent
 */
Optional<Auction> findById(long id);

List<Auction> findAllAfterEventDate(Date date);

List<Auction> findAllByAuctionTypeIdAndAfterEventDate(long auctionTypeId, @NotNull Date start);

List<Auction> findAllByAuctionTypeId(long auctionTypeId);
List<Auction> findHeadersAllByAdminIdSortedByEventDateDesc(long adminId);
void save(Auction auction) throws ResourceModifyingException;
}
