package com.example.usermanagementapp.outcall.repository;

import com.example.usermanagementapp.model.ExternalApplication;
import com.example.usermanagementapp.model.Token;
import com.example.usermanagementapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TokenRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExternalApplicationRepository externalApplicationRepository;


    private RowMapper<Token> tokenRowMapper = new RowMapper<Token>() {
        @Override
        public Token mapRow(ResultSet rs, int rowNum) throws SQLException {
            Token token = new Token();
            token.setId(rs.getLong("id"));
            token.setToken(rs.getString("token"));
            token.setExpirationEnumId(rs.getString("expiration_enum_id"));
            token.setExpiration(rs.getTimestamp("expiration_time").toLocalDateTime());
            Long externalApplicationId = rs.getLong("external_application_id");
            ExternalApplication externalApplication1 = externalApplicationRepository.findById(externalApplicationId).orElse(null);
            token.setExternalApplication(externalApplication1);
            Long userId = rs.getLong("user_id");
            User user = userRepository.findById(userId).orElse(null);
            token.setUser(user);
            return token;
        }
    };

    public List<Token> findAll() {
        String sql = "SELECT * FROM tokens";
        return jdbcTemplate.query(sql, tokenRowMapper);
    }

    public List<Token> findByUserId(Long userId) {
        String sql = "SELECT * FROM tokens WHERE user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, tokenRowMapper);
    }

    public void save(Token token) {
        if (token.getId() == null) {
            String sql = "INSERT INTO tokens (token, external_application_id, user_id, expiration_enum_id, expiration_time) VALUES (?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, token.getToken(), token.getExternalApplication().getId(), token.getUser().getId(), token.getExpirationEnumId(), token.getExpiration());
        } else {
            String sql = "UPDATE tokens SET token = ?, external_application_id = ? WHERE id = ?";
            jdbcTemplate.update(sql, token.getToken(), token.getExternalApplication().getId(), token.getId());
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM tokens WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Token findByToken(String token) {
        String sql = "SELECT * FROM tokens WHERE token = ?";
        List<Token> tokens = jdbcTemplate.query(sql, new Object[]{token}, tokenRowMapper);
        return tokens.isEmpty() ? null : tokens.get(0);
    }
}