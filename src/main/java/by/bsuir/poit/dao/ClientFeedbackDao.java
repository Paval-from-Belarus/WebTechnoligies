package by.bsuir.poit.dao;

import by.bsuir.poit.bean.ClientFeedback;

import java.util.List;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
public interface ClientFeedbackDao {
List<ClientFeedback> findAllBySellerId(long clientId);
List<ClientFeedback> findAllByCustomerId(long clientId);

List<ClientFeedback> findAllByLotId(long lotId);

List<ClientFeedback> findAllBySellerIdSortedByRankingDesc(long clientId);
List<ClientFeedback> findAllByCustomerIdSortedByRankingDesc(long clientId);
ClientFeedback save(ClientFeedback entity);
}
