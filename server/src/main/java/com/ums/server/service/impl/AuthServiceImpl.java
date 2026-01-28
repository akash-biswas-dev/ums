package com.ums.server.service.impl;

import com.ums.server.dtos.JwtAuthorization;
import com.ums.server.dtos.requests.UserCredentials;
import com.ums.server.dtos.response.Authorization;
import com.ums.server.dtos.response.UserResponse;
import com.ums.server.exceptions.InvalidCredentialsException;
import com.ums.server.exceptions.UserLockedException;
import com.ums.server.exceptions.UserNotFoundException;
import com.ums.server.exceptions.UserProfileNotCompleteException;
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

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UmsUsers authenticate(UserCredentials userCredentials) {
        final UmsUsers user;
        try {
            user = userService.getUserByEmail(userCredentials.email());
        } catch (UserNotFoundException ex) {
            log.error("User not found with email: {}", ex.getMessage());
            throw new InvalidCredentialsException("Invalid username");
        }

        String encodedUserPassword = user.getPassword();

        if (!passwordEncoder.matches(userCredentials.password(), encodedUserPassword)) {
            log.error("Invalid password provided by user: {}", userCredentials.password());
            throw new InvalidCredentialsException("Invalid username or password");
        }

        if (user.getIsLocked()) {
            log.error("Access locked for user: {}", user.getId());
            throw new UserLockedException("Profile locked.");
        }

        if (!user.getIsProfileCompleted()) {
            log.warn("Profile not completed for user: {}", user.getId());
            throw new UserProfileNotCompleteException("User profile not complete yet.");
        }
        return user;
    }

}
