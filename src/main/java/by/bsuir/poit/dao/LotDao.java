package by.bsuir.poit.dao;

import by.bsuir.poit.bean.EnglishLot;
import by.bsuir.poit.bean.Lot;
import by.bsuir.poit.dao.exception.DataAccessException;
import by.bsuir.poit.dao.exception.DataModifyingException;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
public interface LotDao {
Optional<EnglishLot> findEnglishLotById(long id);

Optional<Lot> findById(long id);

List<Lot> findAllByAuctionId(long auctionId);

List<Lot> findAllBySellerId(long sellerId);

List<Lot> findAllByCustomerId(long customerId);
Lot save(Lot lot);
void setAuctionId(long lotId, long auctionId) throws DataAccessException;
void setLotStatus(long lotId, short status) throws DataAccessException;
void setCustomerId(long lotId, long customerId) throws DataAccessException;
void setDeliveryPointId(long lotId, long deliveryPointId) throws DataAccessException;
}
