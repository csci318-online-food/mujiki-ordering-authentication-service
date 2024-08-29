package com.csci318.microservice.authentication.Auth.Jwts;

import com.csci318.microservice.authentication.Auth.Handler.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.security.Key;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${spring.security.jwt.secret}")
    private String secret;

    @Value("${spring.security.jwt.expiration}")
    private long expiration;

    private Key key() {
        byte[] bytes = Base64.getDecoder()
                .decode(secret);
        return new SecretKeySpec(bytes, "HmacSHA256"); }

    // Generate token for user
    public String generateToken(UserDetailsImpl userDetails) {
        JwtBuilder builder = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + expiration))
                .signWith(key(), SignatureAlgorithm.HS512);


        return builder.compact();
    }

    // Generate token for restaurant
//    public String generateTokenForRestaurant(RestaurantDetailsImpl restaurantDetails) {
//        JwtBuilder builder = Jwts.builder()
//                .setSubject(restaurantDetails.getUsername())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date((new Date()).getTime() + expiration))
//                .signWith(key(), SignatureAlgorithm.HS512);
//
//        return builder.compact();
//    }

    // Other methods (parseJwt, getUsernameFromJwtToken, etc.)
    public boolean validateJwtToken(String authToken) {
        if (authToken == null) {
            logger.error("Invalid JWT token: Token is null");
            return false;
        } else if (authToken.isEmpty()) {
            logger.error("Invalid JWT token: empty");
            return false;
        } else if ("{{}}".equals(authToken)) {
            logger.error("Invalid JWT token: malformed");
            return false;
        }
        try {
            Jwts.parser().setSigningKey(key()).build().parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}

