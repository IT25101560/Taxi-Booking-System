package com.taxi.taxibookingsystem.model;

//payment model class
//Stores payment details of a customer
public class Payment {

    //Variables to store payment information
    private String customerName;
    private String route;
    private String type;
    private String distance;
    private String amount;
    private String status;

    // Constructor initializes values when object is created
    public Payment(String customerName, String route, String type, String distance, String amount) {
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
}
