package by.bsuir.poit.services.impl;

import by.bsuir.poit.dto.ClientFeedbackDto;
import by.bsuir.poit.context.Service;
import by.bsuir.poit.dao.ClientFeedbackDao;
import by.bsuir.poit.services.ClientFeedbackService;
import by.bsuir.poit.services.exception.resources.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

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
public List<ClientFeedbackDto> findAllByLotId(long lotId) {
      return clientFeedbackDao.findAllByLotId(lotId);
}

@Override
public Optional<ClientFeedbackDto> findByLotIdAndClientTargetId(long lotId, long clientTargetId) {
      return clientFeedbackDao.findByIdAndTargetId(lotId, clientTargetId);
}

@Override
public Optional<ClientFeedbackDto> findByLotIdAndClientAuthorId(long lotId, long clientAuthorId) {
      return clientFeedbackDao.findByIdAndAuthorId(lotId, clientAuthorId);
}

@Override
public List<ClientFeedbackDto> findAllBySellerId(long clientId) {
      return clientFeedbackDao.findAllBySellerId(clientId);
}

private ResourceNotFoundException newFeedbackNotFoundException(String formatted, Object... args) {
      String reason = String.format(formatted, args);
      final String msg = String.format("failed to find feedback by given reason %s", reason);
      LOGGER.error(msg);
      return new ResourceNotFoundException(msg);

}
}
