package by.bsuir.poit.servlets.command.impl;

import by.bsuir.poit.context.RequestHandlerMap;
import by.bsuir.poit.context.Service;
import by.bsuir.poit.servlets.command.RequestHandler;
import by.bsuir.poit.servlets.command.RequestHandlerProvider;
import by.bsuir.poit.servlets.command.RequestMethod;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * Truly, this bean is a part of context (because responsible for constructing other beans)
 * This bean can be constructed only via factory method
 *
 * @author Paval Shlyk
 * @since 27/10/2023
 */
@Service
@NoArgsConstructor
public class RequestHandlerProviderImpl implements RequestHandlerProvider {
private static final Logger LOGGER = LogManager.getLogger(RequestHandlerProviderImpl.class);
@RequestHandlerMap
private Map<RequestMethod, Map<String, RequestHandler>> requestHandlerMap;

@Override
public RequestHandler provide(String url, RequestMethod method) {
      Map<String, RequestHandler> map = requestHandlerMap.get(method);
      RequestHandler requestHandler = map.get(url);
      if (requestHandler == null) {
	    String[] pathVariables = url.split("/");
	    int index = 0;
	    String relativeUrl;
	    while (index < pathVariables.length && requestHandler == null) {
		  relativeUrl = String.join("/", pathVariables[index]);
		  requestHandler = map.get(relativeUrl);
		  index += 1;
	    }
      }
      return requestHandler;
}
}
