package by.bsuir.poit.dao;

import by.bsuir.poit.connections.ConnectionPool;
import by.bsuir.poit.dao.entities.Client;
import by.bsuir.poit.dao.mappers.ClientMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@RequiredArgsConstructor
public class ClientDao {
private final ConnectionPool pool;
private final ClientMapper mapper;
public Optional<Client> findById(long clientId) {
      return Optional.empty();
}
public List<Client> findAllWithStatusAndSortedByRankingDesc(long status) {
      return List.of();
}
}
