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
        String secret = environment.getProperty("ums.jwt.secret");
        String issuer = environment.getProperty("ums.jwt.issuer");
        int accessWindow = 60 * 5; // 5 min

        int expiration = environment.getProperty("ums.jwt.expiration", Integer.class, 86400);
        int maxExpiration = environment.getProperty("ums.jwt.refresh-expiration", Integer.class, 1296000);
        int updateProfileSessionAge = 3600; // 1 hour
        return new JwtServiceImpl(issuer, secret, accessWindow, expiration, maxExpiration, updateProfileSessionAge);
    }
}
