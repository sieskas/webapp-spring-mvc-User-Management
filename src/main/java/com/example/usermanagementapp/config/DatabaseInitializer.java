package com.example.usermanagementapp.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Configuration
public class DatabaseInitializer {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Value("${spring.datasource.driver-class-name}")
    private String dbDriverClassName;

    @Value("${spring.datasource.database-name}")
    private String dbName;

    @PostConstruct
    public void initialize() {
        String masterDbUrl = dbUrl.replace("databaseName=" + dbName, "");
        try {
            Class.forName(dbDriverClassName);
            try (Connection connection = DriverManager.getConnection(masterDbUrl, dbUsername, dbPassword)) {
                try (Statement statement = connection.createStatement()) {
                    String sql = "IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = '" + dbName + "') " +
                            "BEGIN " +
                            "CREATE DATABASE " + dbName + " " +
                            "END";
                    statement.execute(sql);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Failed to create database", e);
        }
    }
}