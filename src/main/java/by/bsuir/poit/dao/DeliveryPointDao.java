package by.bsuir.poit.dao;

import by.bsuir.poit.dao.entities.DeliveryPoint;

import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
public interface DeliveryPointDao {

Optional<DeliveryPoint> findById(long id);
}

