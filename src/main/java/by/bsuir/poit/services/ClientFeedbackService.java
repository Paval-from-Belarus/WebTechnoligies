package by.bsuir.poit.services;

import by.bsuir.poit.bean.ClientFeedback;

import java.util.List;

/**
 * @author Paval Shlyk
 * @since 08/11/2023
 */
public interface ClientFeedbackService {
@Deprecated
List<ClientFeedback> findAllByLotId(long lotId);
ClientFeedback findByLotIdAndClientTargetId(long lotId, long clientTargetId);
ClientFeedback findByLotIdAndClientAuthorId(long lotId, long clientAuthorId);
List<ClientFeedback> findAllBySellerId(long clientId);
}
