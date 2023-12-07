package by.bsuir.poit.dao;


import by.bsuir.poit.model.Auction;
import by.bsuir.poit.services.exception.resources.ResourceModifyingException;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
public interface AuctionDao extends JpaRepository<Auction, Long> {
/**
 * @param id the id of auction
 * @return instance Auction or its corresponding inherent
 */
Optional<Auction> findById(long id);

List<Auction> findAllAfterEventDate(Date date);

List<Auction> findAllByAuctionTypeIdAndEndDateAfter(long auctionTypeId, @NotNull Date start);

List<Auction> findAllByAuctionTypeId(long auctionTypeId);

List<Auction> findHeadersAllByAdminIdOrderByEndDateDesc(long adminId);
}
