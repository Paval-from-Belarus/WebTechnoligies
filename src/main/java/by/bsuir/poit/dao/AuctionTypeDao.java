package by.bsuir.poit.dao;

import by.bsuir.poit.dao.entities.AuctionType;

import java.util.List;
import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
public interface AuctionTypeDao {
Optional<AuctionType> findById(long typeId);

List<AuctionType> findAll();
}
