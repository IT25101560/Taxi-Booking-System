package com.taxi.taxibookingsystem.model;

/**
 * Represents a user in the taxi booking system.
 * This class serves as a blueprint for user data (Admin, Drivers, and Passengers).
 */
public class User {
    // Attributes holding user credentials and information
    protected String username;
    protected String password;
    protected String role;
    protected String email;

    // Constructor to initialize a new User object
    public User(String username, String password, String role, String email) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
    }

    // Getters to safely retrieve private/protected data
    public String getUsername() { return username; }
    public String getRole() { return role; }

    // Converts the user details into a format that is seperated by commas for text file storage
    @Override
    public String toString() {
        return username + "," + password + "," + role + "," + email;
    }
}