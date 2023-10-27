package by.bsuir.poit.servlets.command;

/**
 * @author Paval Shlyk
 * @since 27/10/2023
 */
@FunctionalInterface
public interface RequestHandler {
String handle(String request);
}
