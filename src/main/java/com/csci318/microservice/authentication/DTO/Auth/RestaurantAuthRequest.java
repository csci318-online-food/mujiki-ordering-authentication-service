package com.csci318.microservice.authentication.DTO.Auth;

public class RestaurantAuthRequest {
    private String email;
    private String password;

    public RestaurantAuthRequest() {
    }

    public RestaurantAuthRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setEmail(String username) {
        this.email = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
