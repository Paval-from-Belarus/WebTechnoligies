package by.bsuir.poit.services.impl;

import by.bsuir.poit.dao.ClientRepository;
import by.bsuir.poit.dao.UserRepository;
import by.bsuir.poit.dto.ClientDto;
import by.bsuir.poit.dto.UserDto;
import by.bsuir.poit.dto.mappers.ClientMapper;
import by.bsuir.poit.dto.mappers.UserMapper;
import by.bsuir.poit.services.UserService;
import by.bsuir.poit.services.exception.authorization.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * @author Paval Shlyk
 * @since 08/11/2023
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
private final ClientRepository clientRepository;
private final UserRepository userDao;
private final UserMapper userMapper;
private final ClientMapper clientMapper;
@Override
public ClientDto findClientByUserId(long userId) {
      return clientRepository.findById(userId)
                 .map(clientMapper::toDto)
                 .orElseThrow(() -> newClientNotFoundException(userId));
}

@Override
public UserDto findUserByUserId(long userId) {
      return userDao.findById(userId)
                 .map(userMapper::toDto)
                 .orElseThrow(() -> newUserNotFoundException(userId));
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
