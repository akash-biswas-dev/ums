package com.ums.server.exceptions.handler;

import com.ums.server.exceptions.UserProfileNotCompleteException;
import com.ums.server.service.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class UserProfileNotCompleteExceptionHandle {

    private static final String UPDATE_PROFILE_PATH = "/api/v1/users/update-profile";

    private final JwtService jwtService;

    @ExceptionHandler(UserProfileNotCompleteException.class)
    public void userProfileNotCompleteExceptionHandle(
            UserProfileNotCompleteException ex,
            HttpServletResponse response) throws IOException {
        String session = jwtService.generateTemporaryToken(ex.getUserId());
        Cookie cookie = new Cookie("updateprofile",session);
        cookie.setMaxAge(jwtService.getProfileUpdateSessionAge());
        cookie.setHttpOnly(true);
        cookie.setPath(UPDATE_PROFILE_PATH);
        response.addCookie(cookie);
        response.sendRedirect("/update-profile");
    }
}
