package com.csci318.microservice.authentication.Controller;

import com.csci318.microservice.authentication.Clients.UserServiceClient;
import com.csci318.microservice.authentication.DTO.UserDTO;
import com.csci318.microservice.authentication.DTO.UserDTORequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final UserServiceClient userServiceClient;

//    @Autowired
//    private JwtUtils jwtUtils;
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Autowired
//    private RestaurantDetailsServiceImpl restaurantDetailsService;

    public AuthController(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
        log.info("AuthController initialized");
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTORequest userDTORequest) {
        log.info("Creating user: " + userDTORequest.getUsername());
        UserDTO user = userServiceClient.createUser(userDTORequest);
        return ResponseEntity.ok(user);
    }





}
