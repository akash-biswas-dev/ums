package com.ums.server.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    UserDetails extractAuthentication(String token);

    <T extends UserDetails> String generateToken(T userDetails);

    Integer getAge();
}
