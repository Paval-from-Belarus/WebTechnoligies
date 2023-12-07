package by.bsuir.poit.controller;

import by.bsuir.poit.dto.Create;
import by.bsuir.poit.dto.LotDto;
import by.bsuir.poit.services.LotService;
import by.bsuir.poit.utils.ControllerUtils;
import by.bsuir.poit.utils.PageUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;

/**
 * The handler is supposed to be request handler for any action on lot.
 * But the basic functionality is publishing of a new lot.
 *
 * @author Paval Shlyk
 * @since 08/11/2023
 */
@Controller
@RequestMapping("/api/lot/new")
@RequiredArgsConstructor
public class LotCreationHandler {
private final LotService lotService;

@PostMapping
public void accept(
    @RequestPart @Validated(Create.class) LotDto lot,
    HttpServletRequest request,
    HttpServletResponse response) throws Exception {
//      Lot lot = ParserUtils.parseLot(request);
      lotService.save(request.getUserPrincipal(), lot);
      //the functionality to create a new lot is available only for admin
      PageUtils.redirectTo(response, ControllerUtils.CLIENT_ENDPOINT);
}

}
