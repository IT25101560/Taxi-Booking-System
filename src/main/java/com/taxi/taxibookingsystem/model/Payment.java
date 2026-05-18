package com.taxi.taxibookingsystem.model;

public class Payment {
    private String customerName;
    private String route;
    private String type;
    private String distance;
    private String amount;
    private String status;

    public Payment(String customerName, String route, String type, String distance, String amount) {
        this.customerName = customerName;
        this.route = route;
        this.type = type;
        this.distance = distance;
        this.amount = amount;
        this.status = "PAID";
    }

    // Formats for payment.txt log
    @Override
    public String toString() {
        return "Customer: " + customerName + " | Route: " + route + " | Type: " + type +
                " | Distance: " + distance + " km | Amount: Rs." + amount + " | Status: " + status;
    }
}