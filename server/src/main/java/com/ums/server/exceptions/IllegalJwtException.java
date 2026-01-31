package com.ums.server.exceptions;

public class IllegalJwtException extends RuntimeException {
    public IllegalJwtException(String message) {
        super(message);
    }
}
