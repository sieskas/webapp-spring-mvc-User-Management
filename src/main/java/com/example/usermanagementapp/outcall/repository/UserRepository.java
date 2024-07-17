package com.example.usermanagementapp.outcall.repository;

import com.example.usermanagementapp.model.Role;
import com.example.usermanagementapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> userRowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    };

    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        List<User> users = jdbcTemplate.query(sql, new Object[]{id}, userRowMapper);
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        List<User> users = jdbcTemplate.query(sql, new Object[]{email}, userRowMapper);
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    public User save(User user) {
        if (user.getId() == null) {
            // Insert
            String sql = "INSERT INTO users (email, password) VALUES (?, ?); SELECT SCOPE_IDENTITY();";
            Long generatedId = jdbcTemplate.queryForObject(sql, Long.class, user.getEmail(), user.getPassword());
            user.setId(generatedId);
        } else {
            // Update
            String sql = "UPDATE users SET email = ?, password = ? WHERE id = ?";
            jdbcTemplate.update(sql, user.getEmail(), user.getPassword(), user.getId());
        }
        return user;
    }

    public void delete(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void addRoleToUser(Long userId, Long roleId) {
        String sql = "INSERT INTO user_roles (user_id, role_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, userId, roleId);
    }

    public void removeRoleFromUser(Long userId, Long roleId) {
        String sql = "DELETE FROM user_roles WHERE user_id = ? AND role_id = ?";
        jdbcTemplate.update(sql, userId, roleId);
    }

    public Set<Role> findRolesForUser(Long userId) {
        String sql = "SELECT r.* FROM roles r JOIN user_roles ur ON r.id = ur.role_id WHERE ur.user_id = ?";
        List<Role> roleList = jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) -> {
            Role role = new Role();
            role.setId(rs.getLong("id"));
            role.setName(rs.getString("name"));
            return role;
        });
        return new HashSet<>(roleList);
    }

    public void deleteUserRoles(Long id) {
        String sql = "DELETE FROM user_roles WHERE user_id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<User> findUsersWithOnlyRole(Long roleId) {
        String sql = "SELECT u.id, u.email FROM users u " +
                "JOIN user_roles ur ON u.id = ur.user_id " +
                "GROUP BY u.id, u.email " +
                "HAVING COUNT(DISTINCT ur.role_id) = 1 AND MAX(ur.role_id) = ?";

        return jdbcTemplate.query(sql, new Object[]{roleId}, (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setEmail(rs.getString("email"));
            return user;
        });
    }

    public void updateUserRoles(Long userId, Set<Role> newRoles) {
        // 1. Récupérer les rôles actuels de l'utilisateur
        Set<Long> currentRoleIds = new HashSet<>(jdbcTemplate.queryForList(
                "SELECT role_id FROM user_roles WHERE user_id = ?",
                Long.class,
                userId
        ));

        // 2. Déterminer les rôles à ajouter et à supprimer
        Set<Long> newRoleIds = newRoles.stream().map(Role::getId).collect(Collectors.toSet());
        Set<Long> rolesToAdd = new HashSet<>(newRoleIds);
        rolesToAdd.removeAll(currentRoleIds);
        Set<Long> rolesToRemove = new HashSet<>(currentRoleIds);
        rolesToRemove.removeAll(newRoleIds);

        // 3. Supprimer les rôles qui ne sont plus associés
        if (!rolesToRemove.isEmpty()) {
            jdbcTemplate.batchUpdate(
                    "DELETE FROM user_roles WHERE user_id = ? AND role_id = ?",
                    rolesToRemove.stream()
                            .map(roleId -> new Object[]{userId, roleId})
                            .collect(Collectors.toList())
            );
        }

        // 4. Ajouter les nouveaux rôles
        if (!rolesToAdd.isEmpty()) {
            jdbcTemplate.batchUpdate(
                    "INSERT INTO user_roles (user_id, role_id) VALUES (?, ?)",
                    rolesToAdd.stream()
                            .map(roleId -> new Object[]{userId, roleId})
                            .collect(Collectors.toList())
            );
        }
    }
}