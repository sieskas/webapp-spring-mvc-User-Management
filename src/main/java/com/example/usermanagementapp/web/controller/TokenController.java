package com.example.usermanagementapp.web.controller;

import com.example.usermanagementapp.model.ExpirationEnum;
import com.example.usermanagementapp.model.Token;
import com.example.usermanagementapp.model.User;
import com.example.usermanagementapp.service.TokenService;
import com.example.usermanagementapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/tokens")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @PostMapping
    public String saveToken(Token token) throws Exception {
        User user = userService.getAuthenticatedUser();
       tokenService.createToken(user, token);
        return "redirect:/tokens";
    }

    @GetMapping
    public String listTokens(Model model) throws Exception {
        User user = userService.getAuthenticatedUser();
        List<Token> tokens = tokenService.getTokensByUser(user.getUsername());
        model.addAttribute("tokens", tokens);
        model.addAttribute("expirationOptions", Arrays.asList(ExpirationEnum.values()));
        return "user/token/list";
    }

    @GetMapping("/form")
    public String tokenForm(Model model) {
        model.addAttribute("token", new Token());
        model.addAttribute("externalApplications", tokenService.getExternalApplications());
        model.addAttribute("expirationOptions", Arrays.asList(ExpirationEnum.values()));
        return "user/token/form";
    }

    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        tokenService.deleteToken(id);
        return "redirect:/tokens";
    }
}
