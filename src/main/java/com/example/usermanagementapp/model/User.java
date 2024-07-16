package com.example.usermanagementapp.model;

import java.util.HashSet;
import java.util.Set;

public class User {
    private Long id;
    private String email;
    private String password;
    private Set<Role> roles;

    public User() {
        this.roles = new HashSet<>();
    }

    public User(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.roles = new HashSet<>();
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
    }
}