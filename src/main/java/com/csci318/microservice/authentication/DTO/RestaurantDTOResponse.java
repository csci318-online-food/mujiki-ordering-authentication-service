package com.csci318.microservice.authentication.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RestaurantDTOResponse {
    private String id;
    private String restaurantName;
    private String restaurantPhone;
    private String email;
    private String cuisine;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Double rating;
    private String description;
    private boolean isOpened;
    private String role;
}
