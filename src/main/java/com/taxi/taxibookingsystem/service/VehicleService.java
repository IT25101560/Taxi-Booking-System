package com.taxi.taxibookingsystem.service;

import org.springframework.stereotype.Service;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleService {

    private final String FILE_NAME = "vehicle-registration.txt";


    public synchronized List<String> getAllVehicles() {
        List<String> vehicles = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return vehicles;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {

                if (!line.trim().isEmpty()) {
                    vehicles.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    public synchronized void addVehicle(String data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            bw.write(data);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void deleteVehicle(int index) {
        List<String> vehicles = getAllVehicles();
        if (index >= 0 && index < vehicles.size()) {
            vehicles.remove(index);
            saveAllVehicles(vehicles);
        }
    }

    public synchronized void editVehicle(int index, String newData) {
        List<String> vehicles = getAllVehicles();
        if (index >= 0 && index < vehicles.size()) {
            vehicles.set(index, newData);
            saveAllVehicles(vehicles);
        }
    }

    private void saveAllVehicles(List<String> vehicles) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String v : vehicles) {
                bw.write(v);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}