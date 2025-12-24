package com.ums.server.dtos.response;

import com.ums.server.dtos.ErrorCode;

public record ErrorResponse(
        ErrorCode error,
        String description
) {
}
