package com.taxi.taxibookingsystem.model;

public class Vehicle {
    private String plateNumber;
    private String model;
    private String category;
    private String status;
    private String ownerName;

    public Vehicle(String plateNumber, String model, String category, String status, String ownerName) {
        this.plateNumber = plateNumber;
        this.model = model;
        this.category = category;
        this.status = status;
        this.ownerName = ownerName;
    }

    public String getOwnerName() { return ownerName; }
    public void setStatus(String status) { this.status = status; }

    // Formats for vehicle-registration.txt
    @Override
    public String toString() {
        return plateNumber + " (" + model + ") - " + category + " - " + status + " - Owned by " + ownerName;
    }
}