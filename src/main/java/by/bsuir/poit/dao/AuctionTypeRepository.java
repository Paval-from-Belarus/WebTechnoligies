package by.bsuir.poit.dao;

import by.bsuir.poit.model.AuctionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
public interface AuctionTypeRepository extends JpaRepository<AuctionType, Long> {
Optional<AuctionType> findById(long typeId);
}
