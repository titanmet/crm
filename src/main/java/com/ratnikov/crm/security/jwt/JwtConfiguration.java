package com.ratnikov.crm.security.jwt;

import com.google.common.net.HttpHeaders;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@AllArgsConstructor
@Configuration
public class JwtConfiguration {

    @Value("${jwt.private-key}")
    private String key;

    @Value("${jwt.authorizationHeader}")
    private String authorizationHeader;

    @Value("${jwt.tokenPrefix}")
    private String tokenPrefix;

    @Value("${jwt.tokenExpirationAfterDays}")
    private Integer tokenExpirationAfterDays;

    public String getAuthorizationHeader(){
        return HttpHeaders.AUTHORIZATION;
    }
}
