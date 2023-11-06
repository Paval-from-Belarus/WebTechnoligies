package by.bsuir.poit.servlets.command;

/**
 * @author Paval Shlyk
 * @since 27/10/2023
 */
public interface RequestHandlerProvider {
      RequestHandler provide(String url, RequestMethod method);
}
