package com.taxi.taxibookingsystem.service;

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
