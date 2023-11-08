package by.bsuir.poit.dao;

import by.bsuir.poit.bean.ClientFeedback;

import java.util.List;
import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
public interface ClientFeedbackDao {
List<ClientFeedback> findAllBySellerId(long clientId);
List<ClientFeedback> findAllByCustomerId(long clientId);
@Deprecated
List<ClientFeedback> findAllByLotId(long lotId);
Optional<ClientFeedback> findByIdAndAuthorId(long lotId, long authorId);
Optional<ClientFeedback> findByIdAndTargetId(long lotId, long targetId);
List<ClientFeedback> findAllBySellerIdSortedByRankingDesc(long clientId);
List<ClientFeedback> findAllByCustomerIdSortedByRankingDesc(long clientId);
ClientFeedback save(ClientFeedback entity);
}
