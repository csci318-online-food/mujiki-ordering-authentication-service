package com.csci318.microservice.authentication.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTOResponse {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String role;
}
