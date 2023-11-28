package by.bsuir.poit.dao;

import by.bsuir.poit.dto.Client;

import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
public interface ClientDao {
Optional<Client> findById(long clientId);

void save(Client client);
}
