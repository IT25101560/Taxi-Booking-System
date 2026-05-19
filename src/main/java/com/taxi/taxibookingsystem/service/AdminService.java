package com.taxi.taxibookingsystem.service;

import com.taxi.taxibookingsystem.model.User;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service //tells Spring that the class contains service
public class AdminService {

    private final String FILE_NAME = "users.txt";

    public synchronized List<String> getAllUsers() {
        List<String> users = new ArrayList<>();
        File userFile = new File(FILE_NAME);
        if (!userFile.exists()) return users;

        try (BufferedReader br = new BufferedReader(new FileReader(userFile))) {  //efficiently write text data into files
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    users.add(line);
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
        return users;
    }

    public synchronized void addSystemUser(User newUser) { //prevents multiple threads from accessing the method at the same time
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            bw.write(newUser.toString());
            bw.newLine();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public synchronized void deleteUser(String username) {
        File inputFile = new File(FILE_NAME);
        File tempFile = new File("users_temp.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith(username + ",")) {
                    bw.write(line);
                    bw.newLine();
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
        replaceFile(tempFile, inputFile);
    }

    public synchronized void editUser(String originalUsername, User updatedUser) {
        File inputFile = new File(FILE_NAME);
        File tempFile = new File("users_temp.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(originalUsername + ",")) {
                    bw.write(updatedUser.toString());
                } else {
                    bw.write(line);
                }
                bw.newLine();
            }
        } catch (IOException e) { e.printStackTrace(); }
        replaceFile(tempFile, inputFile);
    }

    private void replaceFile(File tempFile, File inputFile) {
        try { Files.move(tempFile.toPath(), inputFile.toPath(), StandardCopyOption.REPLACE_EXISTING); }
        catch (IOException e) { if(inputFile.delete()) tempFile.renameTo(inputFile); }
    }
}
