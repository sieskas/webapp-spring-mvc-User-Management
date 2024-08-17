package com.example.usermanagementapp.web.controller;

import com.example.usermanagementapp.model.User;
import com.example.usermanagementapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public String home(Model model) throws Exception {
        User user = userService.getAuthenticatedUser();
        String username = user.getUsername();
        model.addAttribute("username", username);
        return "home";
    }

//
//    @GetMapping("/home")
//    public String home(OAuth2AuthenticationToken authentication, Model model) {
//        if (authentication != null) {
//            // Traitez les informations de l'utilisateur ici si nécessaire
//        }
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String username = auth.getName();
//        model.addAttribute("username", username);
//        return "home";
//    }
}