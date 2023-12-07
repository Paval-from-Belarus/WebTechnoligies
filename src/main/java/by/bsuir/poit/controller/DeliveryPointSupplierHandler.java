package by.bsuir.poit.controller;

import by.bsuir.poit.utils.PageUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/api/delivery/points/all")
public class DeliveryPointSupplierHandler {
@GetMapping
public void accept(HttpServletRequest request, HttpServletResponse response) throws Exception {
      PageUtils.redirectTo(response, PageUtils.ERROR_PAGE);
}
}
