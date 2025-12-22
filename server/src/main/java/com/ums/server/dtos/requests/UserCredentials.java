package com.ums.server.dtos.requests;

public record UserCredentials(
        String emailOrEnrollmentId,
        String password
) {
}
