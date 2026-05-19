package com.taxi.taxibookingsystem.controller;

import com.taxi.taxibookingsystem.service.PaymentService;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller // Marks this class as a Spring controller
public class PaymentController {

    // Automatically inject PaymentService object
    @Autowired
    private PaymentService paymentService;


    // Handles payment requests
    @PostMapping("/pay-booking")

    @ResponseBody
    public String payBooking(

            // Receives booking data from frontend
            @RequestParam("bookingDetail")
            String bookingDetail,

            HttpSession session) {


        // Get current logged user
        String username =
                (String) session.getAttribute("user");

        // Check user login
        if(username != null){

            // Send payment data to service layer
            paymentService.processPayment(
                    username,
                    bookingDetail
            );

            return "success";
        }

        return "error";
    }
}
