package com.example.usermanagementapp.model;

import java.time.LocalDateTime;

public class Token {

    private Long id;
    private String token;
    private LocalDateTime expiration;
    private String  expirationEnumId;
    private ExpirationEnum  expirationEnum;
    private ExternalApplication externalApplication;
    private User user;

    public Token() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ExpirationEnum getExpirationEnum() {
        return expirationEnum;
    }

    public void setExpirationEnum(ExpirationEnum  expirationEnum) {
        this.expirationEnum = expirationEnum;
        this.expirationEnumId = expirationEnum.getId();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ExternalApplication getExternalApplication() {
        return externalApplication;
    }

    public void setExternalApplication(ExternalApplication externalApplication) {
        this.externalApplication = externalApplication;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }
    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }

    public String getExpirationEnumId() {
        return expirationEnumId;
    }

    public void setExpirationEnumId(String expirationEnumId) {
        this.expirationEnum = ExpirationEnum.fromId(expirationEnumId);
        this.expirationEnumId = expirationEnumId;
    }
}
