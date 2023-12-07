package by.bsuir.poit.dao;

import by.bsuir.poit.model.DeliveryPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
public interface DeliveryPointRepository extends JpaRepository<DeliveryPoint, Long> {
Optional<DeliveryPoint> findById(long id);
}

