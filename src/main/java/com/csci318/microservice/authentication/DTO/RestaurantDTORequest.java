package com.csci318.microservice.authentication.DTO;

import com.csci318.microservice.authentication.DTO.ObjValue.PhoneNumber;
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
    private PhoneNumber phone;
    private String email;
    private String password;
    private String cuisine;
    private LocalTime openTime;
    private LocalTime closeTime;
    private String description;
}