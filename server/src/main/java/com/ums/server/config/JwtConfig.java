package com.ums.server.config;


import com.ums.server.service.JwtService;
import com.ums.server.service.impl.JwtServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;


@Configuration
@RequiredArgsConstructor
public class JwtConfig {
    private final Environment environment;

    @Bean
    JwtService jwtService() {
        String secret = environment.getProperty("jwt.secret");
        String issuer = environment.getProperty("jwt.issuer");
        Long expiration = environment.getProperty("jwt.expiration", Long.class);
        return new JwtServiceImpl(issuer, secret, expiration);
    }
}
