package by.bsuir.poit.controller;

import by.bsuir.poit.dto.UserDto;
import by.bsuir.poit.context.RequestHandlerDefinition;
import by.bsuir.poit.servlets.command.RequestHandler;
import by.bsuir.poit.utils.AuthorizationUtils;
import by.bsuir.poit.utils.ControllerUtils;
import by.bsuir.poit.utils.PageUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthorizationHandler {
private static final Logger LOGGER = LogManager.getLogger(AuthorizationHandler.class);

public void accept(HttpServletRequest request, HttpServletResponse response) throws Exception {
      UserDto user = (UserDto) request.getAttribute(AuthorizationUtils.USER_ATTRIBUTE);
      assert user != null;
      if (user.getRole() == UserDto.ADMIN) {
	    PageUtils.redirectTo(response, ControllerUtils.ADMIN_ENDPOINT);
	    return;
      }
      if (user.getRole() == UserDto.CLIENT) {
	    PageUtils.redirectTo(response, ControllerUtils.CLIENT_ENDPOINT);
	    return;
      }
      response.sendError(HttpServletResponse.SC_FORBIDDEN);
}
}
