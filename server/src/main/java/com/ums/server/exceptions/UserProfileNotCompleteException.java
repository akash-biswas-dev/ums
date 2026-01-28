package com.ums.server.exceptions;

public class UserProfileNotCompleteException extends RuntimeException {

    private final String userId;

    public UserProfileNotCompleteException(String userId) {
        super(String.format("Profile not complete for user: %s",userId));
        this.userId = userId;
    }

    public String getUserId() {
        return this.userId;
    }
}
