package by.bsuir.poit.controller;

import by.bsuir.poit.utils.PageUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Paval Shlyk
 * @since 12/11/2023
 */
@Controller
@RequestMapping("/api/auction/register")
public class ClientAuctionRegistrationHandler {

public void accept(HttpServletRequest request, HttpServletResponse response) throws Exception {
      PageUtils.redirectTo(response, PageUtils.ERROR_PAGE);
}
}
