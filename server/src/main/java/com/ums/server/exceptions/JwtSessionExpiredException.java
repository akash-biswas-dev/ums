package com.ums.server.exceptions;


public class JwtSessionExpiredException extends RuntimeException{
    public JwtSessionExpiredException(String message) {
        super(message);
    }
}
