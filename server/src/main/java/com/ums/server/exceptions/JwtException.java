package com.ums.server.exceptions;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.AuthenticationException;

public class JwtException extends AuthenticationException {
    public JwtException(@Nullable String msg) {
        super(msg);
    }
}
