package by.bsuir.poit.dao;

import by.bsuir.poit.dao.entities.Client;

import java.util.List;
import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
public interface ClientDao {
Optional<Client> findById(long clientId);
List<Client> findAllWithStatusAndSortedByRankingDesc(long status);
}
