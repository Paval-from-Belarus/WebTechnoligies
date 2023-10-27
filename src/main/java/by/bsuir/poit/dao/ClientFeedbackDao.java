package by.bsuir.poit.dao;

import by.bsuir.poit.bean.ClientFeedback;

import java.util.List;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
public interface ClientFeedbackDao {
List<ClientFeedback> findAllByClientId(long clientId);

List<ClientFeedback> findAllByLotId(long lotId);

List<ClientFeedback> findAllByClientIdSortedByRankingDesc(long clientId);
}
