package com.csci318.microservice.authentication.Controller;

import com.csci318.microservice.authentication.Auth.Handler.RestaurantDetailsImpl;
import com.csci318.microservice.authentication.Auth.Handler.UserDetailsImpl;
import com.csci318.microservice.authentication.Auth.Jwts.JwtUtils;
import com.csci318.microservice.authentication.DTO.Auth.RestaurantAuthRequest;
import com.csci318.microservice.authentication.DTO.Auth.RestaurantAuthResponse;
import com.csci318.microservice.authentication.DTO.RestaurantDTORequest;
import com.csci318.microservice.authentication.DTO.RestaurantDTOResponse;
import com.csci318.microservice.authentication.Services.RestaurantServiceClient;
import com.csci318.microservice.authentication.Services.UserServiceClient;
import com.csci318.microservice.authentication.DTO.Auth.UserAuthRequest;
import com.csci318.microservice.authentication.DTO.Auth.UserAuthResponse;
import com.csci318.microservice.authentication.DTO.UserDTORequest;
import com.csci318.microservice.authentication.DTO.UserDTOResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private final RestaurantServiceClient restaurantServiceClient;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    UserDetailsService userDetailsService;

    @Autowired
    @Qualifier("restaurantDetailsServiceImpl")
    UserDetailsService restaurantDetailsService;

    @Autowired
    JwtUtils jwtUtils;

    public AuthController(UserServiceClient userServiceClient, RestaurantServiceClient restaurantServiceClient) {
        this.userServiceClient = userServiceClient;
        this.restaurantServiceClient = restaurantServiceClient;
        log.info("AuthController initialized");
    }

    @PostMapping("/user/login")
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

    @PostMapping("/user/signup")
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

    @PostMapping("/restaurant/login")
    public ResponseEntity<?> restaurantLogin(@RequestBody RestaurantAuthRequest restaurantAuthRequest) {
        long startTime = System.currentTimeMillis();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(restaurantAuthRequest.getEmail(), restaurantAuthRequest.getPassword()));
        } catch (BadCredentialsException err) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
        }

        long authEndTime = System.currentTimeMillis();
        final RestaurantDetailsImpl restaurant = (RestaurantDetailsImpl) restaurantDetailsService.loadUserByUsername(restaurantAuthRequest.getEmail());
        final String jwt = jwtUtils.generateTokenForRestaurant(restaurant);

        long tokenGenerationEndTime = System.currentTimeMillis();
        final String email = restaurant.getUsername();
        final String role = restaurant.getAuthorities().toString();
        final String restaurantId = restaurant.getId();

        // Testing Time taken for authentication and token generation
        log.info("Restaurant {} logged in successfully", email);
        log.info("Restaurant {} has role {}", email, role);
        log.info("Authentication time: {} ms", (authEndTime - startTime));
        log.info("Token generation time: {} ms", (tokenGenerationEndTime - authEndTime));

        return ResponseEntity.ok(new RestaurantAuthResponse(jwt, restaurantId , email, role));
    }

    @PostMapping("/restaurant/signup")
    public ResponseEntity<?> createRestaurant(@RequestBody RestaurantDTORequest restaurantDTORequest) {
        log.info("Creating restaurant: " + restaurantDTORequest.getEmail());

        if (restaurantDTORequest.getPassword() == null || restaurantDTORequest.getPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password cannot be empty");
        }

        try {
            RestaurantDTOResponse restaurant = restaurantServiceClient.createRestaurant(restaurantDTORequest);
            return ResponseEntity.ok(restaurant);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating restaurant: " + e.getMessage());
        }
    }


}
