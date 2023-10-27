package by.bsuir.poit.dao.impl;

import by.bsuir.poit.dao.ClientDao;
import by.bsuir.poit.dao.connections.ConnectionPool;
import by.bsuir.poit.bean.Client;
import by.bsuir.poit.bean.mappers.ClientMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@RequiredArgsConstructor
public class ClientDaoImpl implements ClientDao {
private final ConnectionPool pool;
private final ClientMapper mapper;

@Override
public Optional<Client> findById(long clientId) {
      return Optional.empty();
}

@Override
public List<Client> findAllWithStatusAndSortedByRankingDesc(long status) {
      return null;
}
}
