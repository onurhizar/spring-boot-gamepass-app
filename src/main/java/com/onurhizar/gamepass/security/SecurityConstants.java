package com.onurhizar.gamepass.security;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
@Getter
public class SecurityConstants {

    @Value("${jwt.secret}")
    private String JWT_SECRET_KEY;

    @Value("${jwt.issuer}")
    private String JWT_TOKEN_ISSUER;

    @Value("${jwt.expiration.hours}")
    private int JWT_EXPIRATION_HOURS;
}
