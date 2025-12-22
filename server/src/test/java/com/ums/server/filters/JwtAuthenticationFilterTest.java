package com.ums.server.filters;

import com.ums.server.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private UserDetails user;

    @BeforeEach
    void beforeEach() {
        user = User.builder()
                .username("user")
                .password("password")
                .authorities("user:read", "user:write")
                .build();
    }
    @AfterEach
    void afterEach() {
       SecurityContextHolder.clearContext();
    }

    @Test
    void shouldUpdateTheSecurityContext() throws ServletException, IOException {
        String token = "a-long-token";

        when(request.getCookies()).thenReturn(new Cookie[]{new Cookie("token", token)});
        when(jwtService.extractAuthentication(token)).thenReturn(user);

        jwtAuthenticationFilter.doFilter(request, response, filterChain);

        SecurityContext context = SecurityContextHolder.getContext();
        assertNotNull(context);
        assertNotNull(context.getAuthentication());
    }
}