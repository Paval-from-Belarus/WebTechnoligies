package by.bsuir.poit.services;

import by.bsuir.poit.bean.User;
import by.bsuir.poit.services.exception.authorization.UserAccessViolationException;
import by.bsuir.poit.services.exception.authorization.UserNotFoundException;
import by.bsuir.poit.services.exception.resources.ResourceNotFoundException;

/**
 * @author Paval Shlyk
 * @since 27/10/2023
 */
public interface AuthorizationService {

/**
 * Mark that user is already sign in to the system. Nobody can be registered to a system several times
 *
 * @param login
 * @param password
 * @throws ResourceNotFoundException
 */
User signIn(String login, String password) throws UserNotFoundException, UserAccessViolationException;

void signOut(long userId);

void register(User user, String password);
}
