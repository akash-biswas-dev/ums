package com.ums.server.exceptions;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthorizationExpired extends AuthenticationException {
    public JwtAuthorizationExpired(String message) {
        super(message);
    }
}
