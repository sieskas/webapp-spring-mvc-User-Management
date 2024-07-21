package com.example.usermanagementapp.model;

public class ExternalApplication {
    private Long id;
    private String name;
    private String authorized_url;

    // Constructors
    public ExternalApplication() {
    }

    public ExternalApplication(Long id, String name, String authorizedUrl) {
        this.id = id;
        this.name = name;
        authorized_url = authorizedUrl;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorized_url() {
        return authorized_url;
    }

    public void setAuthorized_url(String authorized_url) {
        this.authorized_url = authorized_url;
    }
}
