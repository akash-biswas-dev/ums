package com.ums.server.exceptions.handler;

import com.ums.server.dtos.ErrorCode;
import com.ums.server.dtos.response.ErrorResponse;
import com.ums.server.exceptions.InvalidAuthenticationException;
import com.ums.server.exceptions.InvalidCredentialsException;
import com.ums.server.exceptions.JwtAuthorizationExpired;
import com.ums.server.exceptions.SessionExpiredException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;


@Slf4j
@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class AuthExceptionHandle {

    @ExceptionHandler(SessionExpiredException.class)
    public void expiredSessionExceptionHandle(HttpServletResponse response) throws IOException {
        response.sendRedirect("/auth");
    }

    @ExceptionHandler(value = {InvalidAuthenticationException.class})
    public ResponseEntity<ErrorResponse> jwtTokenExceptionHandle(RuntimeException e) {
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.AUTHENTICATION_ERROR, e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtAuthorizationExpired.class)
    public ResponseEntity<ErrorResponse> expiredTokenExceptionHandle(JwtAuthorizationExpired e) {
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.AUTHORIZATION_EXPIRED, e.getMessage()), HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(value = InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleException(InvalidCredentialsException e) {
        ErrorResponse response = new ErrorResponse(ErrorCode.INVALID_CREDENTIALS, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponse> handleException(AuthorizationDeniedException e) {
        ErrorResponse response = new ErrorResponse(ErrorCode.ACCESS_DENIED,e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
}
