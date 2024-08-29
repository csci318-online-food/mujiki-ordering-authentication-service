package com.csci318.microservice.authentication.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RestaurantDTORequest {
    private String name;
    private String phone;
    private String email;
    private String password;
    private String cuisine;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Double rating;
    private String description;
    private boolean isOpened;
}