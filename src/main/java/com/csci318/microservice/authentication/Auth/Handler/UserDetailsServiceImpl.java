package com.csci318.microservice.authentication.Auth.Handler;

import com.csci318.microservice.authentication.Services.UserServiceClient;
import com.csci318.microservice.authentication.DTO.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserServiceClient userServiceClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userServiceClient.getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return UserDetailsImpl.build(user);
    }
}
