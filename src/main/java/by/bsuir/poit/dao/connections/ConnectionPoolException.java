package by.bsuir.poit.dao.connections;

import by.bsuir.poit.dao.impl.DataAccessException;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
public class ConnectionPoolException extends DataAccessException {
public ConnectionPoolException(String message) {
      super(message);
}

public ConnectionPoolException(Throwable t) {
      super(t);
}
}
