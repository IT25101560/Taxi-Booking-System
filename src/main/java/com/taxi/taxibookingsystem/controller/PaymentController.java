package com.taxi.taxibookingsystem.controller;

import com.taxi.taxibookingsystem.service.PaymentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/pay-booking")
    @ResponseBody
    public String payBooking(@RequestParam("bookingDetail") String bookingDetail, HttpSession session) {
        String username = (String) session.getAttribute("user");
        if (username != null) {
            paymentService.processPayment(username, bookingDetail);
            return "success";
        }
        return "error";
    }
}