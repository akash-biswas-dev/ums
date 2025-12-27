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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Nested
    class GenerateJwtCookie {

        @Mock
        private JwtService jwtService;

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
            this.authService = new AuthServiceImpl(jwtService, userService, passwordEncoder);
        }

        @Test
        void shouldGenerateCookieWhenPassCorrectUserCredentials() {

            String token = "token";
            int age = 3600;

            UserCredentials credentials = new UserCredentials(user.getEmail(), user.getPassword());

            when(userService.loadUserByEmail(user.getEmail())).thenReturn(user);

            when(passwordEncoder.matches(user.getPassword(), user.getPassword())).thenReturn(true);
            when(jwtService.generateSession(user.getId(), false)).thenReturn(token);
            when(jwtService.getAge()).thenReturn(age);



            JwtAuthorization cookie = authService.generateJwtCookie(credentials, false);

            assertEquals(token, cookie.token());
            assertEquals(age, cookie.age());
        }

        @Test
        void shouldThrowInvalidCredentialsExceptionWhenPassingInvalidUserCredentials() {

            when(userService.loadUserByEmail(user.getEmail())).thenThrow(exception);

            UserCredentials credentials = new UserCredentials(user.getEmail(), user.getPassword());

            assertThrows(InvalidCredentialsException.class, () -> authService.generateJwtCookie(credentials, false));

        }

    }

}