package com.example.usermanagementapp.web.controller;

import com.example.usermanagementapp.model.Role;
import com.example.usermanagementapp.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public String listRoles(Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        return "role/list";
    }

    @GetMapping("/form")
    public String roleForm(Model model) {
        model.addAttribute("role", new Role());
        return "role/form";
    }

    @PostMapping
    public String saveRole(@ModelAttribute Role role) {
        roleService.saveRole(role);
        return "roles";
    }

    @GetMapping("/{id}/edit")
    public String editRole(@PathVariable Long id, Model model) {
        model.addAttribute("role", roleService.getRoleById(id));
        return "role/form";
    }

    @PostMapping("/{id}/delete")
    public String deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return "roles";
    }
}