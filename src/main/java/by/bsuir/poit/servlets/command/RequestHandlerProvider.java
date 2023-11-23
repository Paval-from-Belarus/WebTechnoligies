package by.bsuir.poit.servlets.command;

/**
 * @author Paval Shlyk
 * @since 27/10/2023
 */
public interface RequestHandlerProvider {
/**
 * Provides a request handler for the specified URL and request method.
 *
 * @param url    the URL of the request
 * @param method the request method
 * @return the request handler for the given URL and request method
 */
RequestHandler provide(String url, RequestMethod method);
}
