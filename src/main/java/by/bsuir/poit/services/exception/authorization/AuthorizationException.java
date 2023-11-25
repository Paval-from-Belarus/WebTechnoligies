package by.bsuir.poit.services.exception.authorization;

/**
 * @author Paval Shlyk
 * @since 27/10/2023
 */
public class AuthorizationException extends RuntimeException {
public AuthorizationException(String message) {
      super(message);
}

public AuthorizationException(Throwable t) {
      super(t);
}
}
