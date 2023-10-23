package by.bsuir.poit.servlets;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
public class UserAccessViolationException extends IllegalStateException{
public UserAccessViolationException(String msg) {
      super(msg);
}
public UserAccessViolationException(Throwable t) {
      super(t);
}

}
