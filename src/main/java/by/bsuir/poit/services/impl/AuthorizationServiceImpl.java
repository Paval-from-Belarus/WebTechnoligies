package by.bsuir.poit.services.impl;

import by.bsuir.poit.bean.Client;
import by.bsuir.poit.bean.User;
import by.bsuir.poit.bean.mappers.ClientMapper;
import by.bsuir.poit.bean.mappers.UserMapper;
import by.bsuir.poit.context.Service;
import by.bsuir.poit.dao.ClientDao;
import by.bsuir.poit.dao.UserDao;
import by.bsuir.poit.dao.exception.DataAccessException;
import by.bsuir.poit.dao.exception.DataModifyingException;
import by.bsuir.poit.services.AuthorizationService;
import by.bsuir.poit.services.exception.authorization.AuthorizationException;
import by.bsuir.poit.services.exception.authorization.UserAccessViolationException;
import by.bsuir.poit.services.exception.authorization.UserNotFoundException;
import by.bsuir.poit.services.exception.resources.ResourceBusyException;
import by.bsuir.poit.services.exception.resources.ResourceModifyingException;
import by.bsuir.poit.utils.AuthorizationUtils;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Paval Shlyk
 * @since 27/10/2023
 */
@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {
private static final Logger LOGGER = LogManager.getLogger(AuthorizationServiceImpl.class);
private final UserDao userDao;
private final ClientDao clientDao;
private final ClientMapper clientMapper;

@Override
public User signIn(String login, String password) {
      User user;
      try {
	    user = userDao.findByUserName(login).orElseThrow(() -> newUserNotFoundException(login));
	    String salt = user.getSecuritySalt();
	    String passwordHash = AuthorizationUtils.encodePassword(password, salt);
	    if (!user.getPasswordHash().equals(passwordHash)) {
		  final String msg = String.format("Invalid password for user with login=%s", login);
		  LOGGER.info(msg);
		  throw new UserAccessViolationException(msg);
	    }
	    if (user.getStatus() == User.STATUS_ACTIVE) {
		  userDao.setUserStatus(user.getId(), User.STATUS_NOT_ACTIVE);
		  final String msg = String.format("User with login=%s is already active", login);
		  LOGGER.warn(msg);
		  // TODO: 27/10/2023 send message to change password
		  throw new AuthorizationException(msg);
	    }
	    userDao.setUserStatus(user.getId(), User.STATUS_ACTIVE);
      } catch (DataAccessException e) {
	    LOGGER.error(e);
	    throw new ResourceBusyException(e);
      }
      return user;
}

@Override
public void signOut(long userId) {
      try {
	    userDao.setUserStatus(userId, User.STATUS_NOT_ACTIVE);
      } catch (DataModifyingException e) {
	    LOGGER.error("Failed to sign-out user {}", e.toString());
	    throw new ResourceBusyException(e);
      }
}

@Override
public void register(@NotNull User user, @NotNull String password) throws ResourceModifyingException {
      boolean isRegistered = false;
      String salt = AuthorizationUtils.newSecuritySalt();
      String passwordHash = AuthorizationUtils.encodePassword(password, salt);
      user.setPasswordHash(passwordHash);
      user.setSecuritySalt(salt);
      try {
	    if (!userDao.existsByName(user.getName())) {
		  userDao.save(user);
		  isRegistered = true;
	    }
	    if (isRegistered && user.getRole() == User.CLIENT) {
		  Client client = clientMapper.fromUser(user);
		  clientDao.save(client);
	    }
      } catch (DataModifyingException e) {
	    LOGGER.warn(e);
	    throw new ResourceModifyingException(e);
      } catch (DataAccessException e) {
	    LOGGER.error(e);
	    throw new ResourceBusyException(e);
      }
      if (!isRegistered) {
	    String msg = String.format("User by name=%s is already exists", user.getName());
	    LOGGER.info(msg);
	    throw new UserAccessViolationException(msg);
      }
}

private static AuthorizationException newUserNotFoundException(String login) {
      final String msg = String.format("Attempt to find not existing user with login=%s", login);
      LOGGER.info(msg);
      return new UserNotFoundException(msg);
}


}
