package by.bsuir.poit.services;

import by.bsuir.poit.bean.ClientFeedback;

import java.util.List;
import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 08/11/2023
 */
public interface ClientFeedbackService {
@Deprecated
List<ClientFeedback> findAllByLotId(long lotId);
Optional<ClientFeedback> findByLotIdAndClientTargetId(long lotId, long clientTargetId);
Optional<ClientFeedback> findByLotIdAndClientAuthorId(long lotId, long clientAuthorId);
List<ClientFeedback> findAllBySellerId(long clientId);
}
