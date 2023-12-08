package by.bsuir.poit.services.impl;

import by.bsuir.poit.dao.UserRepository;
import by.bsuir.poit.dao.exception.DataAccessException;
import by.bsuir.poit.dto.UserDto;
import by.bsuir.poit.model.User;
import by.bsuir.poit.model.UserStatus;
import by.bsuir.poit.services.AuthorizationService;
import by.bsuir.poit.services.exception.authorization.AuthorizationException;
import by.bsuir.poit.services.exception.authorization.UserAccessViolationException;
import by.bsuir.poit.services.exception.resources.ResourceBusyException;
import by.bsuir.poit.servlets.UserDetails;
import by.bsuir.poit.utils.AuthorizationUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author Paval Shlyk
 * @since 21/11/2023
 */
@SpringBootTest
class AuthorizationServiceImplTest {
@MockBean
private UserRepository userDao;
@Autowired
private AuthorizationService authorizationService;

@Test
void signIn() {
      final String validPassword = "123";
      final String invalidPassword = "NOT VALID";
      final String salt = "aboba";
      UserStatus status = mock(UserStatus.class);
      String passwordHash = AuthorizationUtils.encodePassword(validPassword, salt);
      User user = mock(User.class);
      doReturn(passwordHash).when(user).getHash();
      doReturn(salt).when(user).getSalt();
      doReturn(User.STATUS_NOT_ACTIVE).when(status).getId();
      doReturn(status).when(user).getUserStatus();
      when(userDao.findByName(anyString())).thenReturn(Optional.of(user));
      assertDoesNotThrow(() -> authorizationService.signIn(anyString(), validPassword));
      assertThrows(UserAccessViolationException.class, () -> authorizationService.signIn(anyString(), invalidPassword));
      doReturn(User.STATUS_ACTIVE).when(status).getId();
      assertThrows(AuthorizationException.class, () -> authorizationService.signIn(anyString(), validPassword));
      assertDoesNotThrow(() -> authorizationService.signIn(anyString(), validPassword));
}


@Test
void register() {
      User user = mock(User.class);
      UserDto dto = UserDto.builder()
			.name("Buddy")
			.email("pet@mail.su")
			.role(User.ADMIN)
			.build();
      assertDoesNotThrow(() -> authorizationService.register(dto, anyString()));
      doReturn(true).when(userDao).existsByName(anyString());
      assertThrows(UserAccessViolationException.class, () -> authorizationService.register(dto, anyString()));
      doThrow(DataAccessException.class).when(userDao).existsByName(anyString());
      assertThrows(ResourceBusyException.class, () -> authorizationService.register(dto, anyString()));
      doReturn(false).when(userDao).existsByName(anyString());
      doThrow(DataAccessException.class).when(userDao).save(user);
      assertThrows(ResourceBusyException.class, () -> authorizationService.register(dto, anyString()));
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
