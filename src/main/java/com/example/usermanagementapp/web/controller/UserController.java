package com.example.usermanagementapp.web.controller;

import com.example.usermanagementapp.model.Role;
import com.example.usermanagementapp.model.User;
import com.example.usermanagementapp.service.RoleService;
import com.example.usermanagementapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsersWithRoles());
        return "user/list";
    }

    @GetMapping("/form")
    public String userForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "user/form";
    }

    @PostMapping
    public String saveUser(@ModelAttribute User user, @RequestParam(name = "roleIds", required = false) List<Long> roleIds) {
        if (roleIds != null && !roleIds.isEmpty()) {
            Set<Role> selectedRoles = roleService.getRolesByIds(roleIds);
            user.setRoles(selectedRoles);
        } else {
            user.setRoles(new HashSet<>());
        }
        userService.saveUser(user);
        return "redirect:/users";
    }

//    @GetMapping("/{id}")
//    public String viewUser(@PathVariable Long id, Model model) {
//        model.addAttribute("user", userService.getUserById(id));
//        return "user/view";
//    }

    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "user/form";
    }

    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
}
