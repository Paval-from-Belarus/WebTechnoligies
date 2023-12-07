package by.bsuir.poit.dao;


import by.bsuir.poit.model.EnglishLot;
import by.bsuir.poit.model.Lot;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
public interface LotDao extends JpaRepository<Lot, Long> {
@Query("select EnglishLot from EnglishLot where lot.id = ?")
Optional<EnglishLot> findEnglishLotById(long id);

Optional<Lot> findByIdAndStatus(long id, short status);

Optional<Lot> findById(long id);

List<Lot> findAllByAuctionId(long auctionId);

List<Lot> findAllBySellerClientId(long sellerId);

List<Lot> findAllByStatus(short status);

List<Lot> findAllByStatusOrderByStartPriceDesc(short status);

List<Lot> findAllByCustomerClientId(long customerId);

void deleteById(long lotId) throws DataAccessException;

@Query("update Lot set auction.id = :auction_id, status = :status where id = :lot_id")
@Modifying
void assignLotWithStatusToAuction(@Param("lot_id") long lotId, @Param("status") short status, @Param("auction_id") long auctionId);

@Query("update Lot set auction.id = :auction_id where id = :lot_id")
@Modifying
void setAuctionId(@Param("lot_id") long lotId, @Param("auction_id") long auctionId) throws DataAccessException;

@Query("update Lot set status = :status where id = :lot_id")
@Modifying
void setLotStatus(@Param("lot_id") long lotId, @Param("status") short status) throws DataAccessException;

@Query("update Lot set customerClient.id = :customer_id where id = :lot_id")
@Modifying
void setCustomerId(@Param("lot_id") long lotId, @Param("customer_id") long customerId) throws DataAccessException;

@Query("update Lot  set	auctionPrice = :auction_price where id = :lot_id")
@Modifying
void setActualPrice(@Param("lot_id") long lotId, @Param("auction_price") double auctionPrice) throws DataAccessException;

@Query("update Lot set deliveryPoint.id = :delivery_point_id where id = :lot_id")
@Modifying
void setDeliveryPointId(@Param("lot_id") long lotId, @Param("delivery_point_id") long deliveryPointId) throws DataAccessException;
}
