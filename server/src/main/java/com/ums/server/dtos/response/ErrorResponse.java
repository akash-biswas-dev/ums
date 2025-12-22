package com.ums.server.dtos.response;

public record ErrorResponse(
        String error,
        String description
){
}
