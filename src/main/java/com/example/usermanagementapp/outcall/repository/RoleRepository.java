package com.example.usermanagementapp.outcall.repository;

import com.example.usermanagementapp.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Repository
public class RoleRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Role> roleRowMapper = new RowMapper<Role>() {
        @Override
        public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
            Role role = new Role();
            role.setId(rs.getLong("id"));
            role.setName(rs.getString("name"));
            return role;
        }
    };

    public List<Role> findAll() {
        String sql = "SELECT * FROM roles";
        return jdbcTemplate.query(sql, roleRowMapper);
    }

    public Optional<Role> findById(Long id) {
        String sql = "SELECT * FROM roles WHERE id = ?";
        List<Role> roles = jdbcTemplate.query(sql, new Object[]{id}, roleRowMapper);
        return roles.isEmpty() ? Optional.empty() : Optional.of(roles.get(0));
    }

    public Optional<Role> findByName(String name) {
        String sql = "SELECT * FROM roles WHERE name = ?";
        List<Role> roles = jdbcTemplate.query(sql, new Object[]{name}, roleRowMapper);
        return roles.isEmpty() ? Optional.empty() : Optional.of(roles.get(0));
    }

    public Role save(Role role) {
        if (role.getId() == null) {
            // Insert
            String sql = "INSERT INTO role (name) VALUES (?); SELECT SCOPE_IDENTITY();";
            Long generatedId = jdbcTemplate.queryForObject(sql, Long.class, role.getName());
            role.setId(generatedId);
        } else {
            // Update
            String sql = "UPDATE role SET name = ? WHERE id = ?";
            jdbcTemplate.update(sql, role.getName());
        }
        return role;
    }

    public void delete(Long id) {
        String sql = "DELETE FROM roles WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Role> findRolesForUser(Long userId) {
        String sql = "SELECT r.* FROM roles r " +
                "JOIN user_roles ur ON r.id = ur.role_id " +
                "WHERE ur.user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, roleRowMapper);
    }

    public void addRoleToUser(Long userId, Long roleId) {
        String sql = "INSERT INTO user_roles (user_id, role_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, userId, roleId);
    }

    public void removeRoleFromUser(Long userId, Long roleId) {
        String sql = "DELETE FROM user_roles WHERE user_id = ? AND role_id = ?";
        jdbcTemplate.update(sql, userId, roleId);
    }
}