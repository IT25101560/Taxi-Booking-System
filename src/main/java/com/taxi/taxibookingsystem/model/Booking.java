package com.taxi.taxibookingsystem.model;

public class Booking {
    private String customerName;
    private String pickupLocation;
    private String dropoffLocation;
    private String cabType;
    private String distance;
    private String fare;     
    private String status;

    public Booking(String customerName, String pickupLocation, String dropoffLocation, String cabType, String distance, String fare, String status) {
        this.customerName = customerName;
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.cabType = cabType;
        this.distance = distance;
        this.fare = fare;
        this.status = status;
    }

    public void setStatus(String status) { this.status = status; }
    public void setDistanceAndFare(double distance, double fare) {
        this.distance = String.valueOf(distance);
        this.fare = String.valueOf(fare);
    }

  
    @Override
    public String toString() {
        return customerName + " (" + pickupLocation + " to " + dropoffLocation + ")," +
                cabType + "," + distance + "," + fare + "," + status;
    }
}
