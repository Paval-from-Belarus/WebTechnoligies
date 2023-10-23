package by.bsuir.poit.dao;

import by.bsuir.poit.dao.entities.EnglishLot;
import by.bsuir.poit.dao.entities.Lot;

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

List<Lot> findAllByClientId(long lotId);

List<Lot> findAllByClientIdSortedByStartPriceDesc(long clientId);
}
