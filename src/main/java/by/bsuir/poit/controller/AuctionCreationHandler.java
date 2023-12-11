package by.bsuir.poit.controller;

import by.bsuir.poit.dto.AuctionDto;
import by.bsuir.poit.dto.Create;
import by.bsuir.poit.services.AuctionService;
import by.bsuir.poit.utils.ControllerUtils;
import by.bsuir.poit.utils.PageUtils;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;

import java.security.Principal;

/**
 * @author Paval Shlyk
 * @since 27/10/2023
 */
@Controller
@RequestMapping("/api/auction/new")
@RequiredArgsConstructor
public class AuctionCreationHandler {
private final AuctionService auctionService;

@PostMapping
public void accept(
    @NotNull Principal principal,
    @RequestPart @Validated(Create.class) AuctionDto auction,
    HttpServletResponse response) throws Exception {
      auctionService.saveAuction(principal, auction);
      PageUtils.redirectTo(response, ControllerUtils.ADMIN_ENDPOINT);
}
}
