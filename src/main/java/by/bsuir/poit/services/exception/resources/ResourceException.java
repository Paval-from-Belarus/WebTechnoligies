package by.bsuir.poit.services.exception.resources;

/**
 * @author Paval Shlyk
 * @since 27/10/2023
 */
public class ResourceException extends RuntimeException {
public ResourceException(String message) {
      super(message);
}

public ResourceException(Throwable t) {
      super(t);
}
}
