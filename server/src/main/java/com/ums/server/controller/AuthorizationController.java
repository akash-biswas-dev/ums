package com.ums.server.controller;


import com.ums.server.dtos.response.AuthToken;
import com.ums.server.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/authorize")
public class AuthorizationController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<AuthToken> refreshToken(Authentication authentication) {
        String userId = (String) authentication.getPrincipal();
        AuthToken tokens = authService.generateAuthTokens(userId);
        return new ResponseEntity<>(tokens, HttpStatus.CREATED);
    }
}
