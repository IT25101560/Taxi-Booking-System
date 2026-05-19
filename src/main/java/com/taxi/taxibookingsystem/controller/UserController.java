package com.taxi.taxibookingsystem.controller;

import com.taxi.taxibookingsystem.model.User;
import com.taxi.taxibookingsystem.service.DriverService;
import com.taxi.taxibookingsystem.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Handles all web routing and user actions (login, registration, logout).
 */
@Controller
public class UserController {

    // Automatically injects the business logic services
    @Autowired
    private UserService userService;

    @Autowired
    private DriverService driverService;

    // Handles the base URL (/) and routes users
    @GetMapping("/")
    public String root(HttpSession session) {
        if (session.getAttribute("role") != null) return determineRedirect(session);
        return "redirect:/login";
    }

    // Displays the login page, or redirects the user if already logged in
    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        if (session.getAttribute("role") != null) return determineRedirect(session);
        return "login";
    }

    // Processes the login form submission
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        // Validate credentials and get user role
        String role = userService.authenticateUser(username, password);

        if (role != null) {
            // Store user details in the HTTP session for persistence across pages
            session.setAttribute("role", role);
            session.setAttribute("user", "admin".equals(username) ? "Administrator" : username);
            return determineRedirect(session); // Send them to their correct landing page
        }

        // If login fails, show an error message on the login page
        model.addAttribute("error", "Invalid username or password!");
        return "login";
    }

    // Displays the registration page
    @GetMapping("/register")
    public String registerPage(HttpSession session) {
        if (session.getAttribute("role") != null) return "redirect:/home";
        return "register";
    }

    // Processes the registration form for new passengers
    @PostMapping("/register")
    public String registerUser(@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("password") String password, HttpSession session, Model model) {

        // 1. Pack the form data into a new User object
        User newPassenger = new User(name, password, "USER", email);

        // 2. Try to save the user via the UserService
        if (userService.registerPassenger(newPassenger)) {
            // Automatically log the user in after successful registration
            session.setAttribute("role", "USER");
            session.setAttribute("user", name);
            return "redirect:/home";
        }

        // If registration fails, show an error message
        model.addAttribute("error", "Registration failed!");
        return "register";
    }

    // Logs the user out by clearing the session data
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Destroy the session
        return "redirect:/login";
    }

    // Helper method to direct users to the correct page based on their specific role
    private String determineRedirect(HttpSession session) {
        if ("ADMIN".equals(session.getAttribute("role"))) return "redirect:/admin";

        String username = (String) session.getAttribute("user");
        // Drivers go to the driver portal, regular passengers go to the home page
        if (driverService.isUserRegisteredDriver(username)) return "redirect:/driver-portal";

        return "redirect:/home";
    }
}