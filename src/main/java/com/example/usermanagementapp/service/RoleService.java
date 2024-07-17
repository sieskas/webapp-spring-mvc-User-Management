package com.example.usermanagementapp.service;

import com.example.usermanagementapp.model.Role;
import com.example.usermanagementapp.model.User;
import com.example.usermanagementapp.outcall.repository.RoleRepository;
import com.example.usermanagementapp.outcall.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    public void deleteRole(Long id) {
        // Vérifier si des utilisateurs n'ont que ce rôle
        List<User> usersWithOnlyThisRole = userRepository.findUsersWithOnlyRole(id);
        if (!usersWithOnlyThisRole.isEmpty()) {
            throw new IllegalStateException("Cannot delete role: some users have only this role. Users: "
                    + usersWithOnlyThisRole.stream().map(User::getEmail).collect(Collectors.joining(", ")));
        }

        // Supprimer les associations avec les utilisateurs
        roleRepository.deleteRoleAssociations(id);

        // Supprimer le rôle
        roleRepository.delete(id);
    }

    public Set<Role> getRolesByIds(List<Long> roleIds) {
        return roleRepository.findRolesByIds(roleIds);
    }
}
