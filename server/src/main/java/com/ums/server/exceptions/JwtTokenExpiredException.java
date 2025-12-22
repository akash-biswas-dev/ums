package com.ums.server.exceptions;

import org.springframework.security.core.AuthenticationException;

public class JwtTokenExpiredException extends AuthenticationException {
    public JwtTokenExpiredException(String message) {
        super(message);
    }
}
