package com.ums.server.controller;


import com.ums.server.dtos.requests.UserCredentials;
import com.ums.server.dtos.response.Authorization;
import com.ums.server.dtos.response.UserResponse;
import com.ums.server.exceptions.InvalidSession;
import com.ums.server.exceptions.SessionExpiredException;
import com.ums.server.models.UmsUsers;
import com.ums.server.service.AuthService;
import com.ums.server.service.JwtService;
import com.ums.server.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final UserService userService;


    @PostMapping
    private ResponseEntity<Authorization> login(
            @RequestBody UserCredentials userCredentials,
            @RequestParam(name = "rememberMe", required = false, defaultValue = "false") Boolean rememberMe,
            HttpServletResponse response
    ) {
        UmsUsers user = authService.authenticate(userCredentials);

//        Generating session and save it to the cookie which used to generate authorization.
        String session = jwtService.generateSession(user.getId(), rememberMe);
        Integer cookieAge = rememberMe ? jwtService.getMaxAge() : jwtService.getAge();
        Cookie newSessionCookie = new Cookie("session", session);
        newSessionCookie.setHttpOnly(true);
        newSessionCookie.setMaxAge(cookieAge);
        newSessionCookie.setPath("/api/v1/auth/refresh-token");
        response.addCookie(newSessionCookie);

        return generateUserResponse(user);
    }


    @PostMapping(value = "/refresh-token")
    public ResponseEntity<Authorization> refreshToken(
            HttpServletRequest request
    ) {
        Optional<Cookie> sessionCookieOptional = Arrays.stream(request.getCookies())
                .filter((cookie) -> cookie.getName().equals("session"))
                .findFirst();
        if (sessionCookieOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Cookie session = sessionCookieOptional.get();

        if (!session.isHttpOnly()) {
            log.error("Trying to generate Authorization with untrusted source.");
            throw new InvalidSession("Untrusted session data.");
        }

        final String userId;
        try {
            userId = jwtService.extractUserId(session.getValue());
        } catch (ExpiredJwtException exception) {
            Claims claims = exception.getClaims();
            log.error("Session expired for user : {}", claims.getSubject());
            throw new SessionExpiredException("Session expired.");
        }
        UmsUsers user = userService.getUserById(userId);

        return generateUserResponse(user);
    }


    private ResponseEntity<Authorization> generateUserResponse(UmsUsers user) {
//        Generate authorization for the user.
        UserResponse userResponse =
                new UserResponse(
                        user.getFirstName(),
                        user.getLastName()
                );
        String authorizationToken = jwtService.generateToken(user);
        Authorization authorization = new Authorization(
                authorizationToken,
                userResponse
        );
        return new ResponseEntity<>(authorization, HttpStatus.CREATED);
    }


}
