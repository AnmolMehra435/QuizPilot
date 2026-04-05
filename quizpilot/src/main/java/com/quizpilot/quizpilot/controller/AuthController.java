package com.quizpilot.quizpilot.controller;

import jakarta.servlet.http.HttpSession;
import com.quizpilot.quizpilot.model.User;
import com.quizpilot.quizpilot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegister() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String role) {

        User user = new User(username, password, role);
        userRepo.save(user);

        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model,
                        HttpSession session) {

        username = username.trim();
        password = password.trim();

        User user = userRepo.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {

            // ✅ STORE USERNAME IN SESSION
            session.setAttribute("username", user.getUsername());

            if (user.getRole().equalsIgnoreCase("ADMIN")) {
                return "redirect:/admin/dashboard";
            } else {
                return "redirect:/student/dashboard";
            }
        }

        model.addAttribute("error", "Invalid credentials");
        return "login";
    }
}