package by.bsuir.poit.services.exception.authorization;

/**
 * @author Paval Shlyk
 * @since 27/10/2023
 */
public class UserAccessViolationException extends AuthorizationException {
public UserAccessViolationException(String message) {
      super(message);
}

public UserAccessViolationException(Throwable t) {
      super(t);
}
}
