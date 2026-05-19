package com.taxi.taxibookingsystem.model;

// Payment model class
// Stores payment details of a customer

public class Payment {

    // Variables to store payment information
    private String customerName;
    private String route;
    private String type;
    private String distance;
    private String amount;
    private String status;

    // Constructor initializes values when object is created
    public Payment(String customerName,
                   String route,
                   String type,
                   String distance,
                   String amount) {

        this.customerName = customerName;
        this.route = route;
        this.type = type;
        this.distance = distance;
        this.amount = amount;

        // Default payment status
        this.status = "PAID";
    }

    // Convert object into readable text format
    @Override
    public String toString() {

        return "Customer: " + customerName
                + " | Route: " + route
                + " | Type: " + type
                + " | Distance: " + distance + " km"
                + " | Amount: Rs." + amount
                + " | Status: " + status;
    }
} package com.taxi.taxibookingsystem.controller;

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
} package com.taxi.taxibookingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

@Service // Business logic layer
public class PaymentService {

    @Autowired
    private BookingService bookingService;


    // Process customer payment
    public void processPayment(
            String username,
            String bookingDetail){

        // Split booking information
        String[] parts =
                bookingDetail.split(",");


        // Ensure all required values exist
        if(parts.length>=5){

            // Create payment record
            String log=
            "Customer: "+username+
            " | Route:"+parts[0]+
            " | Type:"+parts[1]+
            " | Distance:"+parts[2]+" km"+
            " | Amount:Rs."+parts[3]+
            " | Status:PAID";

            try(

                    // Open payment file
                    BufferedWriter bw=
                    new BufferedWriter(
                    new FileWriter(
                    "payment.txt",true))

            ){

                // Write payment details
                bw.write(log);

                // Move to next line
                bw.newLine();

            }

            catch(IOException e){

                // Handle file error
                e.printStackTrace();
            }


            // Get old status
            String oldStatus=
                    parts[4].trim();

            // Avoid duplicate payments
            if(!oldStatus.contains("PAID")){

                bookingService
                .updateBookingFileStatus(
                        bookingDetail,
                        oldStatus+" - PAID"
                );
            }
        }
    }
}
