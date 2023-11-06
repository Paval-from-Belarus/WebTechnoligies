package by.bsuir.poit.context.exception;

/**
 * @author Paval Shlyk
 * @since 06/11/2023
 */
public class BeanNotFoundException extends RuntimeException {
public BeanNotFoundException(String message) {
      super(message);
}

public BeanNotFoundException(Throwable t) {
      super(t);
}
}
