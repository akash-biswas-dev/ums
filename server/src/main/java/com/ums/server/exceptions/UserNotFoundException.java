package com.ums.server.exceptions;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException{
    private final String username;
    public UserNotFoundException(String username) {
        super(String.format("User not found with username %s", username));
        this.username = username;
    }
}
