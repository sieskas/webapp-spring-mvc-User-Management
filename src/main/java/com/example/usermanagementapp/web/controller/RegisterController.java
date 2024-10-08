package com.example.usermanagementapp.web.controller;

import com.example.usermanagementapp.model.AuthProvider;
import com.example.usermanagementapp.model.User;
import com.example.usermanagementapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUserAccount(User user) throws Exception {
        userService.registerNewUser(user, AuthProvider.INTERNAL);
        return "redirect:/login?registered";
    }
}