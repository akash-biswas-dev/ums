package com.ums.server.service.impl;

import com.ums.server.dtos.JwtAuthorization;
import com.ums.server.dtos.requests.UserCredentials;
import com.ums.server.exceptions.InvalidCredentialsException;
import com.ums.server.exceptions.UserNotFoundException;
import com.ums.server.models.UmsUsers;
import com.ums.server.service.JwtService;
import com.ums.server.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Nested
    class GenerateJwtCookie {

        @Mock
        private UserService userService;

        @Mock
        private PasswordEncoder passwordEncoder;

        private final UserNotFoundException exception = new UserNotFoundException("User not found");

        private AuthServiceImpl authService;

        private UmsUsers user;

        @BeforeEach
        void beforeEach() {
            this.user = UmsUsers.builder().email("email").password("password").build();
            this.authService = new AuthServiceImpl(userService, passwordEncoder);
        }

        @Test
        void shouldGenerateCookieWhenPassCorrectUserCredentials() {

            String token = "token";
            int age = 3600;

            UserCredentials credentials = new UserCredentials(user.getEmail(), user.getPassword());

            when(userService.getUserByEmail(user.getEmail())).thenReturn(user);

            when(passwordEncoder.matches(user.getPassword(), user.getPassword())).thenReturn(true);

        }

        @Test
        void shouldThrowInvalidCredentialsExceptionWhenPassingInvalidUserCredentials() {

            when(userService.getUserByEmail(user.getEmail())).thenThrow(exception);

            UserCredentials credentials = new UserCredentials(user.getEmail(), user.getPassword());


        }

    }

}