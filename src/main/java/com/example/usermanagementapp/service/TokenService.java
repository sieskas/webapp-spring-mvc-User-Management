package com.example.usermanagementapp.service;

import com.example.usermanagementapp.api.v1.rest.resources.TokenRequest;
import com.example.usermanagementapp.model.ExternalApplication;
import com.example.usermanagementapp.model.Token;
import com.example.usermanagementapp.model.User;
import com.example.usermanagementapp.outcall.repository.ExternalApplicationRepository;
import com.example.usermanagementapp.outcall.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TokenService {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private ExternalApplicationRepository externalApplicationRepository;

    public boolean validateToken(TokenRequest tokenRequest) {
        Token storedToken = tokenRepository.findByToken(tokenRequest.getToken());
        if (storedToken == null) {
            return false;
        }
        return true;
    }

    public boolean validateToken(String token, String requestUrl) {
        Token storedToken = tokenRepository.findByToken(token);
        if (storedToken == null) {
            return false;
        }
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(storedToken.getExpiration())) {
            // Le token est expiré, on le supprime de la base de données
            tokenRepository.delete(storedToken.getId());
            return false;
        }
        String authorizedUrl = storedToken.getExternalApplication().getAuthorized_url();
        if (authorizedUrl != null && !requestUrl.startsWith(authorizedUrl)) {
            return false;
        }
        return true;
    }

    public Authentication getAuthentication(String token) {
        Token storedToken = tokenRepository.findByToken(token);
        if (storedToken == null) {
            return null;
        }
        UserDetails userDetails = userService.loadUserByUsername(storedToken.getUser().getEmail());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }


    public String createToken(User user, Token token) {
        String newToken = UUID.randomUUID().toString();
        token.setToken(newToken);
        token.setExpiration(token.getExpirationEnum().calculateExpirationTime());
        if (user != null) {
            token.setUser(user);
        }

        tokenRepository.save(token);
        return newToken;
    }

    public List<Token> getTokensByUser(String username) {
        UserDetails userDetails = userService.loadUserByUsername(username);
        if (userDetails == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        Long userId = ((User) userDetails).getId();
        List<Token> tokens = tokenRepository.findByUserId(userId);
        return cleanExpiredTokens(tokens);
    }

    public List<ExternalApplication> getExternalApplications() {
        return externalApplicationRepository.findAll();
    }

    public void deleteToken(Long id) {
        tokenRepository.delete(id);
    }

    private List<Token> cleanExpiredTokens(List<Token> tokens) {
        LocalDateTime now = LocalDateTime.now();
        List<Token> validTokens = new ArrayList<>();
        for (Token token : tokens) {
            LocalDateTime expirationTime = token.getExpiration();
            if (now.isBefore(expirationTime)) {
                validTokens.add(token);
            } else {
                tokenRepository.delete(token.getId());
            }
        }
        return validTokens;
    }
}