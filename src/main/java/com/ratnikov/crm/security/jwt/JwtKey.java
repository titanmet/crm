package com.ratnikov.crm.security.jwt;

import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
@NoArgsConstructor
@AllArgsConstructor
public class JwtKey {
    private JwtConfiguration jwtConfiguration;

    @Bean
    public SecretKey secretKey() {
        return Keys.hmacShaKeyFor(jwtConfiguration.getKey().getBytes());
    }
}
