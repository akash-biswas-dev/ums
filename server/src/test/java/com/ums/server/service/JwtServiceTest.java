package com.ums.server.service;

import com.ums.server.exceptions.JwtException;
import com.ums.server.exceptions.JwtTokenExpiredException;
import com.ums.server.models.UmsPermissions;
import com.ums.server.models.UmsUsers;
import com.ums.server.service.impl.JwtServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    private JwtService jwtService;
    private UmsUsers user;

    @BeforeEach
    void beforeEach() {
        this.jwtService = new JwtServiceImpl(
                "localhost",
                "59703373367639792F423F452848284D625165546857",
                300,
                86400000L,
                86400000L
        );
        this.user = UmsUsers.builder()
                .id(UUID.randomUUID().toString())
                .email("admin@gmail.com")
                .password("password")
                .permissions(Set.of(UmsPermissions.DIRECTOR_WRITE, UmsPermissions.STUDENT_UPDATE))
                .isEnabled(false)
                .isLocked(false)
                .build();
    }

    @Test
    void shouldCreateTokenWithSubjectUserId() {
        String token = jwtService.generateToken(user);
        UserDetails extractedUser = jwtService.extractAuthentication(token);

        assertEquals(user.getId(), extractedUser.getUsername());
        assertEquals(user.getPermissions().size(), extractedUser.getAuthorities().size());
    }

    @Test
    void shouldThrowExceptionWhenTokenIsInvalid() {
        assertThrows(JwtException.class, () -> {
            jwtService.extractAuthentication("invalid");
        });
    }

    @Test
    void shouldThrowExceptionWhenTokenIsExpired() throws InterruptedException {
        JwtService tempService = new JwtServiceImpl(
                "localhost",
                "59703373367639792F423F452848284D625165546857",
                1,
                100L,
                300L);
        String token = tempService.generateToken(user);
        Thread.sleep(2000);

        assertThrows(JwtTokenExpiredException.class, () -> {
            tempService.extractAuthentication(token);
        });
    }

    @Test
    void shouldThrowExceptionWhenTokenIsSignedByAnotherSecret() {
        JwtService tempService = new JwtServiceImpl(
                "localhost",
                "452848284D62516554685759703373367639792F423F",
                300,
                86400000L,
                86400000L);
        String token = jwtService.generateToken(user);
        assertThrows(JwtException.class, () -> {
            tempService.extractAuthentication(token);
        });
    }

    @Test
    void shouldCreateATokenOnlyWithSubjectUsername() {

        String token = jwtService.generateSession(user.getId(), true);

        String subject = jwtService.extractUserId(token);

        assertEquals(user.getId(), subject);

    }
}