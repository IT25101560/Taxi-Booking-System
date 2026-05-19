package com.taxi.taxibookingsystem.service;

import com.taxi.taxibookingsystem.model.User;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * Handles file reading and writing operations for managing users.
 */
@Service
public class UserService {

    // The text file where all user data is saved
    private final String FILE_NAME = "users.txt";

    // Checks if the given username and password are valid
    public String authenticateUser(String username, String password) {
        // Hardcoded check for the super admin
        if ("admin".equals(username) && "admin123".equals(password)) return "ADMIN";

        File userFile = new File(FILE_NAME);
        if (!userFile.exists()) return null; // Return null if no users have been registered yet

        // Read the text file line by line to search for matching credentials
        try (BufferedReader br = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] details = line.split(","); // Split line by comma into an array

                // details[0] = username, details[1] = password, details[2] = role
                if (details.length >= 2 && details[0].equals(username) && details[1].equals(password)) {
                    return details.length >= 3 ? details[2] : "USER"; // Return user's role, default to 'USER'
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // Return null if no match is found
    }

    // Saves a new passenger object into the text file
    // 'synchronized' prevents data corruption if two users try to register at the exact same millisecond
    public synchronized boolean registerPassenger(User newPassenger) {
        // Open file in append mode (true) so we add to the end instead of overwriting
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            bw.write(newPassenger.toString()); // Write the comma-separated string
            bw.newLine();                      // Move to a new line for the next user
            return true;
        } catch (IOException e) {
            return false; // Return false if a file error occurs
        }
    }
}