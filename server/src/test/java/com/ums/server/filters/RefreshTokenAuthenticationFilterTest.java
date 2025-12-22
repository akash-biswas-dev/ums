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

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RefreshTokenAuthenticationFilterTest {

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private RefreshTokenAuthenticationFilter filter;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;


    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void afterEach() {
        SecurityContextHolder.clearContext();
    }


    @Test
    void shouldUpdateSecurityContextWhenPassValidCookie() throws ServletException, IOException {
        String token = "a-long-token";
        String userId = UUID.randomUUID().toString();

        when(request.getRequestURI()).thenReturn("/api/v1/refresh-token");
        when(request.getCookies()).thenReturn(new Cookie[]{new Cookie("session", token)});
        when(jwtService.extractUserId(token)).thenReturn(userId);

        filter.doFilter(request, response, filterChain);

        SecurityContext context = SecurityContextHolder.getContext();
        assertNotNull(context);
        assertNotNull(context.getAuthentication());
    }

    @Test
    void shouldNotAppliedOtherThatRefreshTokenPath() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/api/v1/secured");
//      If the doFilterInternal method executes then throw JwtException but the requestURI is preventing the execution of the method.
        assertDoesNotThrow(() -> filter.doFilter(request, response, filterChain));
    }
}
