package com.csci318.microservice.authentication.Auth.Handler;

import com.csci318.microservice.authentication.DTO.Restaurant;
import com.csci318.microservice.authentication.DTO.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class RestaurantDetailsImpl implements UserDetails {

    @Getter
    private final String id;
    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public RestaurantDetailsImpl(String id, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(Restaurant restaurant) {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(restaurant.getRole()));

        return new UserDetailsImpl(
                restaurant.getId(),
                restaurant.getEmail(),
                restaurant.getPassword(),
                authorities
        );
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
