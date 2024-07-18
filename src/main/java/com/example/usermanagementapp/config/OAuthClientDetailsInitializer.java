package com.example.usermanagementapp.config;

import com.example.usermanagementapp.model.exceptions.OAuthClientDetailsInitializerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

@Configuration
@DependsOn("flyway")
public class OAuthClientDetailsInitializer {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${oauth.client.id}")
    private String clientId;

    @Value("${oauth.client.secret}")
    private String clientSecret;

    @PostConstruct
    public void init() throws OAuthClientDetailsInitializerException {
        try {
            if (!StringUtils.isEmpty(clientId) && !StringUtils.isEmpty(clientSecret)) {
                String encodedSecret = passwordEncoder.encode(clientSecret);

                String sql = "INSERT INTO oauth_client_details (client_id, client_secret, scope, authorized_grant_types, web_server_redirect_uri, autoapprove) " +
                        "VALUES (?, ?, 'read', 'authorization_code,refresh_token', 'http://localhost:8080/user_management_app_war/oauth/callback', 'true')";

                jdbcTemplate.update(sql, clientId, encodedSecret);
            }
        } catch (DuplicateKeyException e) {
            throw new OAuthClientDetailsInitializerException("Client ID already exists in the database. Please remove the client ID and client secret from the properties file.", e);
        } catch (Exception e) {
            throw e;
        }
    }
}
