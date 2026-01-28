package com.ums.server.service;

import com.ums.server.models.UmsUsers;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    UserDetails extractAuthentication(String token) throws RuntimeException;

    String generateToken(UmsUsers umsUsers);

    String generateSession(String userId, Boolean rememberMe);

    Integer getAge();

    Integer getMaxAge();

    Integer getProfileUpdateSessionAge();

    String extractUserId(String session) throws RuntimeException;

    String generateTemporaryToken(String userId);
}
