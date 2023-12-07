package by.bsuir.poit.controller;

import by.bsuir.poit.dto.Create;
import by.bsuir.poit.dto.UserDto;
import by.bsuir.poit.context.RequestHandlerDefinition;
import by.bsuir.poit.services.AuthorizationService;
import by.bsuir.poit.services.exception.authorization.AuthorizationException;
import by.bsuir.poit.servlets.command.RequestHandler;
import by.bsuir.poit.utils.PageUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

/**
 * @author Paval Shlyk
 * @since 06/11/2023
 */
@Controller
@RequestMapping("/api/reg")
@RequiredArgsConstructor
public class RegistrationHandler {
private static final Logger LOGGER = LogManager.getLogger(RegistrationHandler.class);
private final AuthorizationService authorizationService;

@GetMapping
public void accept(
    @RequestParam("password") String password,
    @RequestPart @Validated(Create.class) UserDto user,
    HttpServletRequest request,
    HttpServletResponse response) throws Exception {
      try {
	    authorizationService.register(user, password);
	    LOGGER.trace("User {} registered successfully", user.getId());
	    PageUtils.redirectTo(response, PageUtils.START_PAGE);
      } catch (AuthorizationException e) {
	    processRegistrationException(e, response);
      } catch (Exception e) {
	    LOGGER.warn("Failed to register user from ip {} with exception: {}", request.getRemoteAddr(), e.getMessage());
	    PageUtils.redirectTo(response, PageUtils.ERROR_PAGE);
      }
}

private void processRegistrationException(AuthorizationException exception, HttpServletResponse response) {
      try {
	    response.getWriter().write(exception.getMessage());
	    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      } catch (Exception e) {
	    LOGGER.error(e.getMessage());
	    throw new IllegalStateException(e);
      }
}
}
