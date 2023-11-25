package by.bsuir.poit.services.exception.resources;

/**
 * The exception will be thrown in case when underlying data access level is not available
 * @author Paval Shlyk
 * @since 27/10/2023
 */
public class ResourceBusyException extends RuntimeException {
public ResourceBusyException(String message) {
      super(message);
}

public ResourceBusyException(Throwable t) {
      super(t);
}
}
