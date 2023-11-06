package by.bsuir.poit.context.exception;

/**
 * @author Paval Shlyk
 * @since 27/10/2023
 */
public class BeanFactoryException extends BeanNotFoundException {
public BeanFactoryException(String message) {
      super(message);
}

public BeanFactoryException(Throwable t) {
      super(t);
}
}
