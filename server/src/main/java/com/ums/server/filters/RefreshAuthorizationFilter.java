package com.ums.server.filters;


import com.ums.server.exceptions.JwtSessionExpiredException;
import com.ums.server.exceptions.JwtTokenExpiredException;
import com.ums.server.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Slf4j
@Component
@RequiredArgsConstructor
public class RefreshAuthorizationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            filterChain.doFilter(request, response);
            return;
        }
        Optional<Cookie> authorizationCookie = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("session")).findFirst();

        if (authorizationCookie.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }
        Cookie cookie = authorizationCookie.get();

        String session = cookie.getValue();
        final String userId;
        try {
            userId = jwtService.extractUserId(session);
            UsernamePasswordAuthenticationToken authentication  = new UsernamePasswordAuthenticationToken(userId, null, List.of());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (JwtTokenExpiredException e) {
            log.error("Session expired {}", e.getMessage());
            throw new JwtSessionExpiredException("Jwt session expired");
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return !path.equals("/api/v1/authorize");
    }
}
