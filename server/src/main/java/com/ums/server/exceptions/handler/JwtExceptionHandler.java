package com.ums.server.exceptions.handler;

import com.ums.server.dtos.response.ErrorResponse;
import com.ums.server.exceptions.JwtException;
import com.ums.server.exceptions.JwtSessionExpiredException;
import com.ums.server.exceptions.JwtTokenExpiredException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;


@Slf4j
@RestControllerAdvice
public class JwtExceptionHandler {
    @ExceptionHandler(JwtSessionExpiredException.class)
    public void expiredSessionExceptionHandle(HttpServletResponse response) throws IOException {
        response.sendRedirect("/auth");
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> jwtTokenExceptionHandle(JwtException e) {
        return new ResponseEntity<>(new ErrorResponse("AUTHENTICATION_ERROR", e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtTokenExpiredException.class)
    public ResponseEntity<ErrorResponse> expiredTokenExceptionHandle(JwtTokenExpiredException e) {
        return new ResponseEntity<>(new ErrorResponse("TOKEN_EXPIRED", e.getMessage()), HttpStatus.UNAUTHORIZED);
    }
}
