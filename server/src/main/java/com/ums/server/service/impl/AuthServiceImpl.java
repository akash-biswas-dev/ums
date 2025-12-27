package com.ums.server.service.impl;

import com.ums.server.dtos.JwtAuthorization;
import com.ums.server.dtos.requests.UserCredentials;
import com.ums.server.dtos.response.Authorization;
import com.ums.server.dtos.response.UserResponse;
import com.ums.server.exceptions.InvalidCredentialsException;
import com.ums.server.exceptions.UserNotFoundException;
import com.ums.server.models.UmsUsers;
import com.ums.server.service.AuthService;
import com.ums.server.service.JwtService;
import com.ums.server.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public JwtAuthorization generateJwtCookie(UserCredentials userCredentials, Boolean rememberMe) {

        final UmsUsers user;
        try {
            user = userService.loadUserByEmail(userCredentials.email());
        } catch (UserNotFoundException ex) {
            log.error("User not found with email: {}", ex.getMessage());
            throw new InvalidCredentialsException("Invalid email or password");
        }

        boolean isPasswordMatches = passwordEncoder.matches(userCredentials.password(), user.getPassword());

        if (!isPasswordMatches) {
            log.error("Invalid password");
            throw new InvalidCredentialsException("Invalid password");
        }

        Authorization authorization = new Authorization(
                jwtService.generateToken(user),
                getUserResponse(user)
        );

        if (rememberMe) {
            Integer age = jwtService.getMaxAge();
            String token = jwtService.generateSession(user.getId(), true);
            return new JwtAuthorization(token, age, authorization);
        }

        return new JwtAuthorization(
                jwtService.generateSession(user.getId(), false),
                jwtService.getAge(),
                authorization
        );
    }

    @Override
    public Authorization generateAuthTokens(String userId) {
        UmsUsers users = userService.loadUserById(userId);
        String token = jwtService.generateToken(users);
        UserResponse userResponse = getUserResponse(users);
        return new Authorization(token, userResponse);
    }

    private UserResponse getUserResponse(UmsUsers umsUsers) {
        return new UserResponse(
                umsUsers.getFirstName(),
                umsUsers.getLastName()
        );
    }
}
