package com.csci318.microservice.authentication.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Restaurant {
    private UUID id;
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
