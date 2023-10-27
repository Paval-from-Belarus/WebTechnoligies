package by.bsuir.poit.dao.impl;

import by.bsuir.poit.dao.ClientFeedbackDao;
import by.bsuir.poit.dao.connections.ConnectionPool;
import by.bsuir.poit.bean.ClientFeedback;
import by.bsuir.poit.bean.mappers.ClientFeedbackMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@RequiredArgsConstructor
public class ClientFeedbackDaoImpl implements ClientFeedbackDao {
private final ConnectionPool pool;
private final ClientFeedbackMapper mapper;

@Override
public List<ClientFeedback> findAllByClientId(long clientId) {
      return null;
}

@Override
public List<ClientFeedback> findAllByLotId(long lotId) {
      return null;
}

@Override
public List<ClientFeedback> findAllByClientIdSortedByRankingDesc(long clientId) {
      return null;
}
}
