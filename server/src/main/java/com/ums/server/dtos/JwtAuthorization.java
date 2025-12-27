package com.ums.server.dtos;

import com.ums.server.dtos.response.Authorization;

public record JwtAuthorization(
        String token,
        Integer age,
        Authorization authorization
) {
}
