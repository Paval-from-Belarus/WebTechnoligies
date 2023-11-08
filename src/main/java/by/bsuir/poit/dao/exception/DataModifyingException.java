package by.bsuir.poit.dao.exception;

/**
 * @author Paval Shlyk
 * @since 27/10/2023
 */
public class DataModifyingException extends DataAccessException {
public DataModifyingException(String message) {
      super(message);
}

public DataModifyingException(Throwable t) {
      super(t);
}
}
