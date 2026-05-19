package com.taxi.taxibookingsystem.controller;

import com.taxi.taxibookingsystem.model.User;
import com.taxi.taxibookingsystem.service.AdminService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller //handles HTTP requests and returns view pages
public class AdminController {

    @Autowired //dependency injection
    private AdminService adminService;

    @GetMapping("/admin") //get http request
    public String adminDashboard(HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("role"))) return "redirect:/login";
        return "admin-dashboard";
    }

    @GetMapping("/users") 
    public String manageUsers(HttpSession session, Model model) {
        if (!"ADMIN".equals(session.getAttribute("role"))) return "redirect:/home";

        // Asks the new AdminService for the list of users
        model.addAttribute("users", adminService.getAllUsers());
        return "user-management";
    }

    @PostMapping("/admin-add-user") //post http request
    public String adminAddUser(@RequestParam String username, @RequestParam String email, @RequestParam String password, @RequestParam String role, HttpSession session) {
        if ("ADMIN".equals(session.getAttribute("role"))) {
            // Uses the Model Object
            User newUser = new User(username, password, role, email);
            adminService.addSystemUser(newUser);
        }
        return "redirect:/users";
    }

    @PostMapping("/delete-user")
    public String deleteUser(@RequestParam String username, HttpSession session) {
        if ("ADMIN".equals(session.getAttribute("role"))) {
            adminService.deleteUser(username);
        }
        return "redirect:/users";
    }

    @PostMapping("/edit-user")
    public String editUser(@RequestParam String originalUsername, @RequestParam String username, @RequestParam String email, @RequestParam String password, @RequestParam String role, HttpSession session) {
        if ("ADMIN".equals(session.getAttribute("role"))) {
            // Uses the Model Object
            User updatedUser = new User(username, password, role, email);
            adminService.editUser(originalUsername, updatedUser);
        }
        return "redirect:/users";
    }
}
