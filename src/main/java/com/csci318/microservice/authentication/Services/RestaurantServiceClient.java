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

    private static final Logger log = LoggerFactory.getLogger(UserServiceClient.class);
    @Autowired
    private RestTemplate restTemplate;

    @Value("${restaurant.url.service}")
    private String RESTAURANT_URL;


    public RestaurantDTOResponse createRestaurant(RestaurantDTORequest restaurantDTORequest) {
        Restaurant restaurant = restTemplate.postForObject(RESTAURANT_URL + "/signup", restaurantDTORequest, Restaurant.class);
        return new RestaurantDTOResponse(restaurant.getId(), restaurant.getName(), restaurant.getPhone(), restaurant.getEmail(), restaurant.getCuisine(), restaurant.getOpenTime(), restaurant.getCloseTime(), restaurant.getRating(), restaurant.getDescription(), restaurant.isOpened());
    }
}
