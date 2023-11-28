package by.bsuir.poit.services.impl;

import by.bsuir.poit.dto.Client;
import by.bsuir.poit.dto.User;
import by.bsuir.poit.context.Service;
import by.bsuir.poit.dao.ClientDao;
import by.bsuir.poit.dao.UserDao;
import by.bsuir.poit.services.UserService;
import by.bsuir.poit.services.exception.authorization.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Paval Shlyk
 * @since 08/11/2023
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
private final ClientDao clientDao;
private final UserDao userDao;

@Override
public Client findClientByUserId(long userId) {
      return clientDao.findById(userId).orElseThrow(() -> newClientNotFoundException(userId));
}

@Override
public User findUserByUserId(long userId) {
      return userDao.findById(userId).orElseThrow(() -> newUserNotFoundException(userId));
}

private static UserNotFoundException newUserNotFoundException(long userId) {
      final String msg = String.format("User by id=%d not found", userId);
      LOGGER.warn(msg);
      throw new UserNotFoundException(msg);

}

private static UserNotFoundException newClientNotFoundException(long clientId) {
      final String msg = String.format("Client by id=%d not found", clientId);
      LOGGER.warn(msg);
      return new UserNotFoundException(msg);

}
}
