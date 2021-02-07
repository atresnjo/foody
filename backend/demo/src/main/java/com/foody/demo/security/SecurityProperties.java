package com.foody.demo.security;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.stereotype.Component;

import java.time.Duration;

@ConstructorBinding
@ConfigurationProperties(prefix = "foody.security")
@Getter
@Component
@RequiredArgsConstructor
public class SecurityProperties {

    /**
     * Amound of hashing iterations, where formula is 2^passwordStrength iterations
     */
    private final int passwordStrength = 10;
    /**
     * Secret used to generate and verify JWT tokens
     */
    private final String tokenSecret = "foody";
    /**
     * Name of the token issuer
     */
    private final String tokenIssuer = "http://localhost:9001";
    /**
     * Duration after which a token will expire
     */
    private final Duration tokenExpiration = Duration.ofHours(4);
}

