package by.bsuir.poit.services.exception.resources;

/**
 * @author Paval Shlyk
 * @since 27/10/2023
 */
public class ResourceModifyingException extends ResourceException {
public ResourceModifyingException(String message) {
      super(message);
}

public ResourceModifyingException(Throwable t) {
      super(t);
}
}
