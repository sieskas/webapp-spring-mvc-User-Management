package com.example.usermanagementapp.outcall.repository;

import com.example.usermanagementapp.model.ExternalApplication;
import com.example.usermanagementapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class ExternalApplicationRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<ExternalApplication> externalApplicationRowMapper = (rs, rowNum) -> {
        ExternalApplication externalApplication = new ExternalApplication();
        externalApplication.setId(rs.getLong("id"));
        externalApplication.setName(rs.getString("name"));
        externalApplication.setAuthorized_url(rs.getString("authorized_url"));
        return externalApplication;
    };

    public List<ExternalApplication> findAll() {
        String sql = "SELECT * FROM external_applications";
        return jdbcTemplate.query(sql, externalApplicationRowMapper);
    }

    public Optional<ExternalApplication> findById(Long id) {
        String sql = "SELECT * FROM external_applications WHERE id = ?";
        List<ExternalApplication> externalApplications = jdbcTemplate.query(sql, new Object[]{id}, externalApplicationRowMapper);
        return externalApplications.isEmpty() ? Optional.empty() : Optional.of(externalApplications.get(0));}



    public void save(ExternalApplication externalApplication) {
        if (externalApplication.getId() == null) {
            String sql = "INSERT INTO external_applications (name, authorized_url) VALUES (?, ?)";
            jdbcTemplate.update(sql, externalApplication.getName(), externalApplication.getAuthorized_url());
        } else {
            String sql = "UPDATE external_applications SET name = ?, authorized_url = ? WHERE id = ?";
            jdbcTemplate.update(sql, externalApplication.getName(), externalApplication.getAuthorized_url(), externalApplication.getId());
        }
    }


    public void delete(Long id) {
        String sql = "DELETE FROM external_applications WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}