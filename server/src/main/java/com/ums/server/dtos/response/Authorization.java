package com.ums.server.dtos.response;

public record Authorization(
        String authorization,
        UserResponse user
) {
}
