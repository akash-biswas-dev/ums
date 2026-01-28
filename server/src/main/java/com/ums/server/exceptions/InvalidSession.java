package com.ums.server.exceptions;

public class InvalidSession extends RuntimeException {
    public InvalidSession(String message) {
        super(message);
    }
}
