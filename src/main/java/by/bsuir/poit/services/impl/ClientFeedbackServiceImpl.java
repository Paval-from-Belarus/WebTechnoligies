package by.bsuir.poit.services.impl;

import by.bsuir.poit.bean.ClientFeedback;
import by.bsuir.poit.context.Service;
import by.bsuir.poit.dao.ClientFeedbackDao;
import by.bsuir.poit.services.ClientFeedbackService;
import by.bsuir.poit.services.exception.resources.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * @author Paval Shlyk
 * @since 08/11/2023
 */
@Service
@RequiredArgsConstructor
public class ClientFeedbackServiceImpl implements ClientFeedbackService {
private static final Logger LOGGER = LogManager.getLogger(ClientFeedbackServiceImpl.class);
private final ClientFeedbackDao clientFeedbackDao;

@Override
public List<ClientFeedback> findAllByLotId(long lotId) {
      return clientFeedbackDao.findAllByLotId(lotId);
}

@Override
public ClientFeedback findByLotIdAndClientTargetId(long lotId, long clientTargetId) {
      return clientFeedbackDao.findByIdAndTargetId(lotId, clientTargetId).orElseThrow(() -> newFeedbackNotFoundException("by lotId=%d and targetId=%d", lotId, clientTargetId));
}

@Override
public ClientFeedback findByLotIdAndClientAuthorId(long lotId, long clientAuthorId) {
      return clientFeedbackDao.findByIdAndAuthorId(lotId, clientAuthorId).orElseThrow(() -> newFeedbackNotFoundException("by lotId=%d and authorId=%d", lotId, clientAuthorId));
}

@Override
public List<ClientFeedback> findAllBySellerId(long clientId) {
      return clientFeedbackDao.findAllBySellerId(clientId);
}

private ResourceNotFoundException newFeedbackNotFoundException(String formatted, Object... args) {
      String reason = String.format(formatted, args);
      final String msg = String.format("failed to find feedback by given reason %s", reason);
      LOGGER.error(msg);
      return new ResourceNotFoundException(msg);

}
}
