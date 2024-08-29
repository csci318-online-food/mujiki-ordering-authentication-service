package com.csci318.microservice.authentication.Auth.Handler;

import com.csci318.microservice.authentication.DTO.Restaurant;
import com.csci318.microservice.authentication.Services.RestaurantServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RestaurantDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantDetailsServiceImpl.class);

    @Autowired
    private RestaurantServiceClient restaurantServiceClient;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Restaurant restaurant = restaurantServiceClient.getRestaurantByEmail(email);

        if (restaurant == null) {
            logger.error("Restaurant not found with email: {}", email);
            throw new UsernameNotFoundException("Restaurant not found with email: " + email);
        }

        return RestaurantDetailsImpl.build(restaurant);
    }
}