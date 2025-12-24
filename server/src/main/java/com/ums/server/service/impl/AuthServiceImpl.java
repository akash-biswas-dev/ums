package com.ums.server.service.impl;

import com.ums.server.dtos.JwtCookie;
import com.ums.server.dtos.requests.UserCredentials;
import com.ums.server.exceptions.InvalidCredentialsException;
import com.ums.server.models.UmsUsers;
import com.ums.server.service.AuthService;
import com.ums.server.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public JwtCookie generateJwtCookie(UserCredentials userCredentials, Boolean rememberMe) {

        final Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userCredentials.email(),
                    userCredentials.password()
            ));
        } catch (AuthenticationException exception) {
            log.error("User not authenticated with user id: {} with message: {} ",
                    userCredentials.email(),
                    exception.getMessage());
            throw new InvalidCredentialsException("Invalid Credentials");
        }
        UmsUsers user = (UmsUsers) authentication.getPrincipal();

        if (rememberMe) {
            Integer age = jwtService.getMaxAge();
            String token = jwtService.generateSession(user.getUsername(), true);
            return new JwtCookie(token, age);
        }

        return new JwtCookie(
                jwtService.generateSession(user.getUsername(), false),
                jwtService.getAge()
        );
    }
}
