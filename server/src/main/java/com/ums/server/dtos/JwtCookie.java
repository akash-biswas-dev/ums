package com.ums.server.dtos;

public record JwtCookie(
        String token,
        Integer age
) {
}
