package by.bsuir.poit.services.impl;

import by.bsuir.poit.dao.ClientRepository;
import by.bsuir.poit.dao.UserRepository;
import by.bsuir.poit.dao.UserStatusRepository;
import by.bsuir.poit.dto.UserDto;
import by.bsuir.poit.dto.mappers.UserMapper;
import by.bsuir.poit.model.Client;
import by.bsuir.poit.model.User;
import by.bsuir.poit.services.AuthorizationService;
import by.bsuir.poit.services.exception.authorization.AuthorizationException;
import by.bsuir.poit.services.exception.authorization.UserAccessViolationException;
import by.bsuir.poit.services.exception.authorization.UserNotFoundException;
import by.bsuir.poit.services.exception.resources.ResourceBusyException;
import by.bsuir.poit.services.exception.resources.ResourceModifyingException;
import by.bsuir.poit.servlets.UserDetails;
import by.bsuir.poit.utils.AuthorizationUtils;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.math.BigDecimal;
import java.security.Principal;

/**
 * @author Paval Shlyk
 * @since 27/10/2023
 */
@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {
private static final Logger LOGGER = LogManager.getLogger(AuthorizationServiceImpl.class);
private final UserRepository userRepository;
private final ClientRepository clientDao;
private final UserMapper userMapper;
private final UserStatusRepository userStatusRepository;

@Override
@Transactional
public UserDto signIn(String login, String password) {
      User user = userRepository
		      .findByName(login)
		      .orElseThrow(() -> newUserNotFoundException(login));
      String salt = user.getSalt();
      String passwordHash = AuthorizationUtils.encodePassword(password, salt);
      if (!user.getHash().equals(passwordHash)) {
	    final String msg = String.format("Invalid password for user with login=%s", login);
	    LOGGER.info(msg);
	    throw new UserAccessViolationException(msg);
      }
      if (user.getUserStatus().getId() == User.STATUS_ACTIVE) {
	    user.setUserStatus(userStatusRepository.getReferenceById(User.STATUS_NOT_ACTIVE));
	    final String msg = String.format("User with login=%s is already active", login);
	    LOGGER.warn(msg);
	    // TODO: 27/10/2023 send message to change password
	    throw new AuthorizationException(msg);
      }
      user.setUserStatus(userStatusRepository.getReferenceById(User.STATUS_ACTIVE));
      return userMapper.toDto(user);

}

@Override
public void signOut(long userId) {
      userRepository.setUserStatus(userId, User.STATUS_NOT_ACTIVE);
}

//this method should be implemented as a stored routine
@Override
@Transactional
public void register(@NotNull UserDto dto, @NotNull String password) throws ResourceModifyingException {
      if (userRepository.existsByName(dto.getName())) {
	    throw newUserAlreadyExistsException(dto);
      }
      String salt = AuthorizationUtils.newSecuritySalt();
      String hash = AuthorizationUtils.encodePassword(password, salt);
      User user = userMapper.toEntity(dto);
      user.setHash(hash);
      user.setSalt(salt);
      user.setUserStatus(userStatusRepository.getReferenceById(User.STATUS_NOT_ACTIVE));
      user = userRepository.save(user);
      if (user.getRole() == User.CLIENT) {
	    Client client = Client.builder()
				.ranking(0.0)
				.account(BigDecimal.ZERO)
				.user(user)
				.build();
	    clientDao.save(client);
      }
}

@ExceptionHandler(DataAccessException.class)
public void handleException(DataAccessException e) {
      LOGGER.warn(e);
      throw new ResourceBusyException(e);
}

@Override
public void verifyByUserAccess(Principal principal, long userId) throws UserAccessViolationException {
      UserDetails details = (UserDetails) principal;
      if (details.role() != User.ADMIN && details.id() != userId) {
	    final String msg = String.format("User rights verification failed for principal %s", details);
	    LOGGER.warn(msg);
	    throw new UserAccessViolationException(msg);
      }
}

@Override
public void verifyByUserRole(Principal principal, short role) throws UserAccessViolationException {
      UserDetails details = (UserDetails) principal;
      if (principal == null || details.role() != role) {
	    final String msg = String.format("User %s has invalid role for requested=%d", principal.getName(), role);
	    LOGGER.error(msg);
	    throw new UserAccessViolationException(msg);
      }
}

@Override
public long getUserIdByPrincipal(Principal principal) throws UserNotFoundException {
      UserDetails details = (UserDetails) principal;
      return details.id();
}

private static AuthorizationException newUserNotFoundException(String login) {
      final String msg = String.format("Attempt to find not existing user with login=%s", login);
      LOGGER.info(msg);
      return new UserNotFoundException(msg);
}

private static AuthorizationException newUserAlreadyExistsException(UserDto dto) {
      final String msg = String.format("The user by given credentials is already exists %s", dto);
      LOGGER.warn(msg);
      throw new UserAccessViolationException(msg);

}


}
