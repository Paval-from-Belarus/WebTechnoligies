package by.bsuir.poit.services.exception.authorization;

/**
 * @author Paval Shlyk
 * @since 27/10/2023
 */
public class UserNotFoundException extends AuthorizationException {
public UserNotFoundException(String message) {
      super(message);
}

public UserNotFoundException(Throwable t) {
      super(t);
}
}
