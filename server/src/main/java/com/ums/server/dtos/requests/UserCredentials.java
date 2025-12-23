package com.ums.server.dtos.requests;

public record UserCredentials(
        String email,
        String password
) {
}
