package by.bsuir.poit.services.impl;

import by.bsuir.poit.dto.User;
import by.bsuir.poit.dto.mappers.ClientMapper;
import by.bsuir.poit.dao.ClientDao;
import by.bsuir.poit.dao.UserDao;
import by.bsuir.poit.dao.exception.DataAccessException;
import by.bsuir.poit.services.exception.authorization.AuthorizationException;
import by.bsuir.poit.services.exception.authorization.UserAccessViolationException;
import by.bsuir.poit.services.exception.resources.ResourceBusyException;
import by.bsuir.poit.servlets.UserDetails;
import by.bsuir.poit.utils.AuthorizationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * @author Paval Shlyk
 * @since 21/11/2023
 */
class AuthorizationServiceImplTest {
private ClientMapper clientMapper;
private UserDao userDao;
private ClientDao clientDao;
private AuthorizationServiceImpl authorizationService;

@BeforeEach
public void initMock() {
      clientMapper = mock(ClientMapper.class);
      userDao = mock(UserDao.class);
      clientDao = mock(ClientDao.class);
      authorizationService = new AuthorizationServiceImpl(userDao, clientDao, clientMapper);
}

@Test
void signIn() {
      final String validPassword = "123";
      final String invalidPassword = "NOT VALID";
      final String salt = "aboba";
      String passwordHash = AuthorizationUtils.encodePassword(validPassword, salt);
      User user = User.builder()
                      .name("Any name")
		      .passwordHash(passwordHash)
                      .securitySalt(salt)
		      .status(User.STATUS_NOT_ACTIVE)
		      .build();
      when(userDao.findByUserName(anyString())).thenReturn(Optional.of(user));
      assertDoesNotThrow(() -> authorizationService.signIn(anyString(), validPassword));
      assertThrows(UserAccessViolationException.class, () -> authorizationService.signIn(anyString(), invalidPassword));
      user.setStatus(User.STATUS_ACTIVE);
      assertThrows(AuthorizationException.class, () -> authorizationService.signIn(anyString(), validPassword));
}


@Test
void register() {
      User user = User.builder()
		      .name("name")
		      .role(User.CLIENT)
		      .build();
      doReturn(false).when(userDao).existsByName(anyString());
      assertDoesNotThrow(() -> authorizationService.register(user, anyString()));
      doReturn(true).when(userDao).existsByName(anyString());
      assertThrows(UserAccessViolationException.class, () -> authorizationService.register(user, anyString()));
      doThrow(DataAccessException.class).when(userDao).existsByName(anyString());
      assertThrows(ResourceBusyException.class, () -> authorizationService.register(user, anyString()));
      //todo: add transactional check in Spring
}

@Test
void verifyByUserAccess() {
      UserDetails details = mock(UserDetails.class);
      final long userId = 12;
      final long otherUserId = 42;
      when(details.id()).thenReturn(userId);
      when(details.role()).thenReturn(User.CLIENT);
      assertDoesNotThrow(() -> authorizationService.verifyByUserAccess(details, userId));
      assertThrows(UserAccessViolationException.class, () -> authorizationService.verifyByUserAccess(details, otherUserId));
      when(details.role()).thenReturn(User.ADMIN);
      assertDoesNotThrow(() -> authorizationService.verifyByUserAccess(details, userId));
      assertDoesNotThrow(() -> authorizationService.verifyByUserAccess(details, otherUserId));
}
}