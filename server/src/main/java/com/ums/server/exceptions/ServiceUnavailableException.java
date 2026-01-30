package com.ums.server.exceptions;


// Thrown when an operation not completed with unknown cause.
public class ServiceUnavailableException extends RuntimeException {
    public ServiceUnavailableException(String message) {
        super(message);
    }
}
