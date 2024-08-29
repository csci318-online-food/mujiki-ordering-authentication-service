package com.csci318.microservice.authentication.Clients;

import com.csci318.microservice.authentication.DTO.User;
import com.csci318.microservice.authentication.DTO.UserDTORequest;
import com.csci318.microservice.authentication.DTO.UserDTOResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceClient {

    private static final Logger log = LoggerFactory.getLogger(UserServiceClient.class);
    @Autowired
    private RestTemplate restTemplate;

    @Value("${user.url.service}")
    private String USER_URL;

    // Get user by username
    public User getUserByUsername(String username) {
        return restTemplate.getForObject(USER_URL + "/" + username, User.class);
    }

    public UserDTOResponse createUser(UserDTORequest userDTORequest) {
        User user = restTemplate.postForObject(USER_URL + "/signup", userDTORequest, User.class);
        log.info("User created: " + user.getPassword());
        return new UserDTOResponse(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhone(), user.getRole());
    }
}