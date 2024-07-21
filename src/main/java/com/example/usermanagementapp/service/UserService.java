package com.example.usermanagementapp.service;

import com.example.usermanagementapp.model.Role;
import com.example.usermanagementapp.model.User;
import com.example.usermanagementapp.outcall.repository.RoleRepository;
import com.example.usermanagementapp.outcall.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerNewUser(User user) {
        //add validation password size, force etc...
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);

        Role defaultRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Default role not found"));
        userRepository.addRoleToUser(savedUser.getId(), defaultRole.getId());

        return savedUser;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getAllUsersWithRoles() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            user.setRoles(userRepository.findRolesForUser(user.getId()));
        }
        return users;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User saveUser(User user) {
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            throw new IllegalArgumentException("User must have at least one role");
        }

        if (user.getId() == null || (user.getPassword() != null && !user.getPassword().isEmpty())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            User existingUser = userRepository.findById(user.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            user.setPassword(existingUser.getPassword());
        }

        User savedUser = userRepository.save(user);

        userRepository.updateUserRoles(savedUser.getId(), user.getRoles());

        return savedUser;
    }

    public void deleteUser(Long id) {
        userRepository.deleteUserRoles(id);
        userRepository.delete(id);
    }

    public void updateUserRoles(Long userId, Set<Role> newRoles) {
        Set<Role> currentRoles = userRepository.findRolesForUser(userId);
        Set<Role> rolesToAdd = newRoles.stream()
                .filter(role -> !currentRoles.contains(role))
                .collect(Collectors.toSet());
        Set<Role> rolesToRemove = currentRoles.stream()
                .filter(role -> !newRoles.contains(role))
                .collect(Collectors.toSet());

        rolesToRemove.forEach(role -> userRepository.removeRoleFromUser(userId, role.getId()));
        rolesToAdd.forEach(role -> userRepository.addRoleToUser(userId, role.getId()));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
    }

    public User getAuthenticatedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            return (User) principal;
        } else {
            throw new UsernameNotFoundException("User not found in security context");
        }
    }
}