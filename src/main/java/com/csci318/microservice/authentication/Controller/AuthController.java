package com.csci318.microservice.authentication.Controller;

import com.csci318.microservice.authentication.Auth.Handler.UserDetailsImpl;
import com.csci318.microservice.authentication.Auth.Jwts.JwtUtils;
import com.csci318.microservice.authentication.Clients.UserServiceClient;
import com.csci318.microservice.authentication.DTO.Auth.UserAuthRequest;
import com.csci318.microservice.authentication.DTO.Auth.UserAuthResponse;
import com.csci318.microservice.authentication.DTO.User;
import com.csci318.microservice.authentication.DTO.UserDTORequest;
import com.csci318.microservice.authentication.DTO.UserDTOResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final UserServiceClient userServiceClient;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtUtils jwtUtils;



    public AuthController(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
        log.info("AuthController initialized");
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody UserAuthRequest userAuthRequest) {
        long startTime = System.currentTimeMillis();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userAuthRequest.getUsername(), userAuthRequest.getPassword()));
        } catch (BadCredentialsException err) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
        }

        long authEndTime = System.currentTimeMillis();
        final UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(userAuthRequest.getUsername());
        final String jwt = jwtUtils.generateToken(userDetails);

        long tokenGenerationEndTime = System.currentTimeMillis();
        final String username = userDetails.getUsername();
        final String role = userDetails.getAuthorities().toString();
        final String userId = userDetails.getId();

        // Testing Time taken for authentication and token generation
        log.info("User {} logged in successfully", username);
        log.info("User {} has role {}", username, role);
        log.info("Authentication time: {} ms", (authEndTime - startTime));
        log.info("Token generation time: {} ms", (tokenGenerationEndTime - authEndTime));

        return ResponseEntity.ok(new UserAuthResponse(jwt, userId, username, role));
    }

//    @PostMapping("/signup")
//    public ResponseEntity<UserDTOResponse> createUser(@RequestBody UserDTORequest userDTORequest) {
//        log.info("Creating user: " + userDTORequest.getUsername());
//        UserDTOResponse user = userServiceClient.createUser(userDTORequest);
//        return ResponseEntity.ok(user);
//    }

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody UserDTORequest userDTORequest) {
        log.info("Creating user: " + userDTORequest.getUsername());

        if (userDTORequest.getPassword() == null || userDTORequest.getPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password cannot be empty");
        }

        try {
            UserDTOResponse user = userServiceClient.createUser(userDTORequest);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user: " + e.getMessage());
        }
    }





}
