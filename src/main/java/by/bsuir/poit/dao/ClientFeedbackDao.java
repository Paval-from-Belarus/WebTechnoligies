package by.bsuir.poit.dao;

import by.bsuir.poit.connections.ConnectionPool;
import by.bsuir.poit.dao.entities.ClientFeedback;
import by.bsuir.poit.dao.mappers.ClientFeedbackMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@RequiredArgsConstructor
public class ClientFeedbackDao {
private final ConnectionPool pool;
private final ClientFeedbackMapper mapper;
public List<ClientFeedback> findAllByClientId(long clientId) {
	return List.of();
}
public List<ClientFeedback> findAllByLotId(long lotId) {
      return List.of();
}
public List<ClientFeedback> findAllByClientIdSortedByRankingDesc(long clientId) {
      return List.of();
}

}
