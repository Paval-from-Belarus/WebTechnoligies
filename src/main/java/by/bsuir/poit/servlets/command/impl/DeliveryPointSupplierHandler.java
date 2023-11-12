package by.bsuir.poit.servlets.command.impl;

import by.bsuir.poit.context.RequestHandlerDefinition;
import by.bsuir.poit.servlets.command.RequestHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Paval Shlyk
 * @since 12/11/2023
 */
@RequestHandlerDefinition(urlPatterns = "/delivery/points/all")
public class DeliveryPointSupplierHandler implements RequestHandler {
@Override
public void accept(HttpServletRequest request, HttpServletResponse response) throws Exception {

}
}
