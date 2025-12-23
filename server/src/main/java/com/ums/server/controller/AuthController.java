package com.ums.server.controller;


import com.ums.server.dtos.JwtCookie;
import com.ums.server.dtos.requests.UserCredentials;
import com.ums.server.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping
    public void login(@RequestBody UserCredentials userCredential,
                      HttpServletResponse response,
                      @RequestParam(name = "rememberMe", required = false, defaultValue = "false") Boolean rememberMe) throws IOException {

        JwtCookie jwtCookie = authService.generateJwtCookie(userCredential, rememberMe);
        Cookie cookie = new Cookie("session", jwtCookie.token());
        cookie.setPath("/api");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(jwtCookie.age());
        response.addCookie(new Cookie("session", cookie.getValue()));
        response.sendRedirect("/ums");
    }
}
