package com.ums.server.service;

import com.ums.server.dtos.JwtCookie;
import com.ums.server.dtos.requests.UserCredentials;
import com.ums.server.exceptions.InvalidCredentialsException;
import com.ums.server.models.UmsUsers;
import com.ums.server.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Nested
    class GenerateJwtCookie {
        @Mock
        private AuthenticationManager authenticationManager;

        @Mock
        private JwtService jwtService;

        @Mock
        private AuthenticationException exception;

        private AuthService authService;

        private UmsUsers user;

        @BeforeEach
        void beforeEach() {
            this.user = UmsUsers.builder().email("email").password("password").build();
            this.authService = new AuthServiceImpl(authenticationManager, jwtService);
        }

        @Test
        void shouldGenerateCookieWhenPassCorrectUserCredentials() {

            String token = "token";
            int age = 3600;

            UserCredentials credentials = new UserCredentials(user.getEmail(), user.getPassword());

            when(authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            )).thenReturn(new UsernamePasswordAuthenticationToken(user, new ArrayList<>()));

            when(jwtService.generateSession(user.getUsername(), false)).thenReturn(token);
            when(jwtService.getAge()).thenReturn(age);


            JwtCookie cookie = authService.generateJwtCookie(credentials, false);

            assertEquals(token, cookie.token());
            assertEquals(age, cookie.age());
        }

        @Test
        void shouldThrowBadCredentialsExceptionWhenPassingInvalidUserCredentials() {

            when(authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            )).thenThrow(exception);

            UserCredentials credentials = new UserCredentials(user.getEmail(), user.getPassword());

            assertThrows(InvalidCredentialsException.class, () -> authService.generateJwtCookie(credentials, false));

        }

    }

}