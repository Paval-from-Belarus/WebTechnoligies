package by.bsuir.poit.servlets.command.impl;

import by.bsuir.poit.context.Service;
import by.bsuir.poit.servlets.command.RequestHandler;
import by.bsuir.poit.servlets.command.RequestHandlerProvider;
import lombok.RequiredArgsConstructor;

/**
 * @author Paval Shlyk
 * @since 27/10/2023
 */
@Service
@RequiredArgsConstructor
public class RequestHandlerProviderImpl implements RequestHandlerProvider {
@Override
public RequestHandler provide(String url) {
      return null;
}

}
