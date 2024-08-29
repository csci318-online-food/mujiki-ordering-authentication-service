package com.csci318.microservice.authentication.Services;

import com.csci318.microservice.authentication.DTO.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestaurantServiceClient {

    private static final Logger log = LoggerFactory.getLogger(RestaurantServiceClient.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${restaurant.url.service}")
    private String RESTAURANT_URL;

    public RestaurantDTOResponse createRestaurant(RestaurantDTORequest restaurantDTORequest) {
        return restTemplate.postForObject(RESTAURANT_URL + "/signup", restaurantDTORequest, RestaurantDTOResponse.class);
    }

    public Restaurant getRestaurantByEmail(String email) {
        try {
            return restTemplate.getForObject(RESTAURANT_URL + "/" + email, Restaurant.class);
        } catch (Exception e) {
            log.error("Error fetching restaurant with email: {}", email, e);
            return null;
        }
    }
}