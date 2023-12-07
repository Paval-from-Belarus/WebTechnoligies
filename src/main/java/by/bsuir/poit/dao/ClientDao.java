package by.bsuir.poit.dao;

import by.bsuir.poit.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
public interface ClientDao extends JpaRepository<Client, LotDao> {
Optional<Client> findById(long clientId);
}
