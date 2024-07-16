package com.example.usermanagementapp.service;

import com.example.usermanagementapp.model.Role;
import com.example.usermanagementapp.model.User;
import com.example.usermanagementapp.outcall.repository.RoleRepository;
import com.example.usermanagementapp.outcall.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerNewUser(User user) {
        // Encoder le mot de passe
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Sauvegarder l'utilisateur
        User savedUser = userRepository.save(user);

        // Ajouter le rôle par défaut (ROLE_USER)
        Role defaultRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Default role not found"));
        userRepository.addRoleToUser(savedUser.getId(), defaultRole.getId());

        return savedUser;
    }
}