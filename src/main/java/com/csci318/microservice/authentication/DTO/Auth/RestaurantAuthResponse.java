package com.csci318.microservice.authentication.DTO.Auth;

public class RestaurantAuthResponse {
    private final String jwt;
    private final String id;
    private final String email;
    private final String role;

    public RestaurantAuthResponse(String jwt, String id, String email, String role) {
        this.jwt = jwt;
        this.id = id;
        this.email = email;
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

    public String getEmail() {
        return email;
    }
}
