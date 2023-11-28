package by.bsuir.poit.dao;

import by.bsuir.poit.dto.EnglishLot;
import by.bsuir.poit.dto.Lot;
import by.bsuir.poit.dao.exception.DataAccessException;
import by.bsuir.poit.dao.exception.DataModifyingException;

import java.util.List;
import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
public interface LotDao {
Optional<EnglishLot> findEnglishLotById(long id);

Optional<Lot> findByIdAndStatus(long id, short status);

Optional<Lot> findById(long id);

List<Lot> findAllByAuctionId(long auctionId);

List<Lot> findAllBySellerId(long sellerId);

List<Lot> findAllByStatus(short status);

List<Lot> findAllByStatusOrderByStartingPriceDesc(short status);

List<Lot> findAllByCustomerId(long customerId);

Lot save(Lot lot);
void assignLotWithStatusToAuction(long lotId, short status, long auctionId);

void delete(long lotId) throws DataAccessException, DataModifyingException;

void setAuctionId(long lotId, long auctionId) throws DataAccessException;

void setLotStatus(long lotId, short status) throws DataAccessException;

void setCustomerId(long lotId, long customerId) throws DataAccessException;
void setActualPrice(long lotId, double auctionPrice) throws DataAccessException;

void setDeliveryPointId(long lotId, long deliveryPointId) throws DataAccessException;
}
