package com.example.usermanagementapp.web.controller;

import com.example.usermanagementapp.service.PowerBIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/powerbi")
public class PowerBIController {

    @Autowired
    private PowerBIService powerBIService;

    @PostMapping("/createDataset")
    public String createDataset(OAuth2AuthenticationToken authentication, Model model) {
        try {
            String response = powerBIService.createDataset(authentication);
            model.addAttribute("message", "Dataset créé avec succès !");
            model.addAttribute("messageType", "success");
        } catch (Exception e) {
            model.addAttribute("message", "Échec de la création du dataset : " + e.getMessage());
            model.addAttribute("messageType", "error");
        }
        return "redirect:/powerbi";
    }

    @GetMapping
    public String powerBiPage(@RequestParam(name = "message", required = false) String message,
                              @RequestParam(name = "messageType", required = false) String messageType,
                              Model model) {
        model.addAttribute("message", message);
        model.addAttribute("messageType", messageType);
        return "powerbi";
    }
}
