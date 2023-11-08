package by.bsuir.poit.services.impl;

import by.bsuir.poit.bean.Client;
import by.bsuir.poit.bean.ClientFeedback;
import by.bsuir.poit.context.Service;
import by.bsuir.poit.dao.ClientDao;
import by.bsuir.poit.dao.ClientFeedbackDao;
import by.bsuir.poit.services.ClientService;
import by.bsuir.poit.services.exception.authorization.UserNotFoundException;
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
public class ClientServiceImpl implements ClientService {
private static final Logger LOGGER = LogManager.getLogger(ClientServiceImpl.class);
private final ClientDao clientDao;

@Override
public Client findClientByUserId(long userId) {
      return clientDao.findById(userId).orElseThrow(() -> newClientNotFoundException(userId));
}

private static UserNotFoundException newClientNotFoundException(long clientId) {
      final String msg = String.format("Client by id=%d not found", clientId);
      LOGGER.warn(msg);
      return new UserNotFoundException(msg);

}
}
