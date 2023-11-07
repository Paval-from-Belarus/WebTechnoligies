package by.bsuir.poit.servlets.exception;

/**
 * @author Paval Shlyk
 * @since 08/11/2023
 */
public class DuplicatedCookieException extends RuntimeException {
public DuplicatedCookieException(String msg) {
      super(msg);
}
}
