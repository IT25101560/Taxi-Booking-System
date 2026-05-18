package com.taxi.taxibookingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class PaymentService {

    @Autowired
    private BookingService bookingService;

    public void processPayment(String username, String bookingDetail) {
        String[] parts = bookingDetail.split(",");
        if (parts.length >= 5) {
            String log = "Customer: " + username + " | Route: " + parts[0] + " | Type: " + parts[1] + " | Distance: " + parts[2] + " km | Amount: Rs." + parts[3] + " | Status: PAID";
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("payment.txt", true))) {
                bw.write(log); bw.newLine();
            } catch (IOException e) { e.printStackTrace(); }

            String oldStatus = parts[4].trim();
            if (!oldStatus.contains("PAID")) {
                bookingService.updateBookingFileStatus(bookingDetail, oldStatus + " - PAID");
            }
        }
    }
}