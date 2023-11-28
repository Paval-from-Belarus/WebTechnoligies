package by.bsuir.poit.services;

import by.bsuir.poit.dto.User;
import by.bsuir.poit.services.exception.authorization.UserAccessViolationException;
import by.bsuir.poit.services.exception.authorization.UserNotFoundException;
import by.bsuir.poit.services.exception.resources.ResourceNotFoundException;

import java.security.Principal;

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

/**
 * Signs out a user with the specified user ID.
 *
 * @param userId the ID of the user to sign out
 */
void signOut(long userId);

/**
 * Registers a new user with the provided user information and password.
 *
 * @param user     the user to register
 * @param password the password for the user
 */

void register(User user, String password);

/**
 * Verifies access of principal to modify any information about user specified by {@code userId}
 * Throws a UserAccessViolationException if the access is not granted.
 *
 * @param principal the principal representing the user
 * @param userId    the ID of the user to verify access for
 * @throws UserAccessViolationException if the access is not granted
 */
void verifyByUserAccess(Principal principal, long userId) throws UserAccessViolationException;

/**
 * Verifies user access based on the provided principal and role.
 * Throws a UserAccessViolationException if the access is not granted.
 *
 * @param principal the principal representing the user
 * @param role      the role to verify access for
 * @throws UserAccessViolationException if the access is not granted
 */
void verifyByUserRole(Principal principal, short role) throws UserAccessViolationException;

/**
 * Retrieves the user ID associated with the provided principal.
 * Throws a UserNotFoundException if the user is not found.
 *
 * @param principal the principal representing the user
 * @return the user ID associated with the principal
 * @throws UserNotFoundException if the user is not found
 */
long getUserIdByPrincipal(Principal principal) throws UserNotFoundException;
}
