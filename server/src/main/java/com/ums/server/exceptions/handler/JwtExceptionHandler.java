package com.ums.server.exceptions.handler;

import com.ums.server.exceptions.JwtTokenExpiredException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;


@Slf4j
@RestControllerAdvice
public class JwtExceptionHandler {
    @ExceptionHandler(JwtTokenExpiredException.class)
    public void expiredJwtTokenExceptionHandle(Exception e, HttpServletResponse response) throws IOException {
        log.error("The token expired with message: {}",e.getMessage());
        response.sendRedirect("/auth");
    }
}
