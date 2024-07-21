package com.example.usermanagementapp.service;

import com.example.usermanagementapp.model.AuthProvider;
import com.example.usermanagementapp.model.Role;
import com.example.usermanagementapp.model.User;
import com.example.usermanagementapp.outcall.repository.RoleRepository;
import com.example.usermanagementapp.outcall.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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

    public User registerNewUser(User user, AuthProvider authProvider) throws Exception {
        if (!AuthProvider.INTERNAL.equals(authProvider)) {
            throw new Exception();
        }
        //add validation password size, force etc...
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthProvider(AuthProvider.INTERNAL);
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

    private User registerNewOAuth2User(String email, AuthProvider authProvider) throws Exception {
        if (AuthProvider.INTERNAL.equals(authProvider)) {
            throw new Exception();
        }
        Role defaultRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Default role not found"));
        User user = new User();
        user.setEmail(email);
        user.setPassword("");
        user.getRoles().add(defaultRole);
        user.setAuthProvider(authProvider);
        User userSaved = userRepository.save(user);
        roleRepository.addRoleToUser(userSaved, defaultRole);
        return userSaved;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
        return user ;
    }

    public User getAuthenticatedUser() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof DefaultOAuth2User) {
            String email;
            AuthProvider authProvider;
            if (AuthProvider.GOOGLE.name().equalsIgnoreCase(((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId())) {
                email = ((DefaultOAuth2User) authentication.getPrincipal()).getAttribute("email");
                authProvider = AuthProvider.GOOGLE;
            } else {
                throw new Exception();
            }
            User user = userRepository.findByEmail(email).orElse(null);
            if (user == null) {
                user = registerNewOAuth2User(email, authProvider);
                user = saveUser(user);
            }
            //authenticateUser(user);
            return user;
        } else if (authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        }
        throw new UsernameNotFoundException("User not found in security context");
    }

    private void authenticateUser(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}