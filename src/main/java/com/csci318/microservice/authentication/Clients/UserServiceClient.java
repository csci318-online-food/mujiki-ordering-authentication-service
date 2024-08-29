package com.csci318.microservice.authentication.Clients;

import com.csci318.microservice.authentication.DTO.UserDTO;
import com.csci318.microservice.authentication.DTO.UserDTORequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${user.url.service}")
    private String USER_URL;

    public UserDTO getUserByUsername(String username) {
        return restTemplate.getForObject(USER_URL + "/" + username, UserDTO.class);
    }

    public UserDTO createUser(UserDTORequest userDTORequest) {
        return restTemplate.postForObject(USER_URL + "/signup", userDTORequest, UserDTO.class);
    }
}