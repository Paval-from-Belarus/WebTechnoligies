package by.bsuir.poit.services.exception.resources;

/**
 * @author Paval Shlyk
 * @since 27/10/2023
 */
public class ResourceNotFoundException extends ResourceException {
public ResourceNotFoundException(String message) {
      super(message);
}

public ResourceNotFoundException(Throwable t) {
      super(t);
}
}
