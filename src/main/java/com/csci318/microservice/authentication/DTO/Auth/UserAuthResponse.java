package com.csci318.microservice.authentication.DTO.Auth;

public class UserAuthResponse {
    private final String jwt;
    private final String id;
    private final String username;
    private final String role;

    public UserAuthResponse(String jwt, String id, String username, String role) {
        this.jwt = jwt;
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public String getJwt() {
        return jwt;
    }

    public String getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }
}
