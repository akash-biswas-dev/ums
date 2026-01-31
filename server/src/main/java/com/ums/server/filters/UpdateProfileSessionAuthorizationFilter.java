package com.ums.server.filters;

import com.ums.server.exceptions.IllegalJwtException;
import com.ums.server.exceptions.SessionExpiredException;
import com.ums.server.service.JwtService;
import com.ums.server.utils.HttpUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;


@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateProfileSessionAuthorizationFilter extends OncePerRequestFilter {

    public static final String UPDATE_PROFILE_SESSION = "update_profile_session";
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        Cookie updateProfileSession = HttpUtils.getCookie(request.getCookies(), UPDATE_PROFILE_SESSION);
        if (!updateProfileSession.isHttpOnly()) {
            log.error("Untrusted resource access with session: {}", updateProfileSession.getValue());
        }

        if (Objects.isNull(updateProfileSession.getValue()) ||
                updateProfileSession.getValue().isEmpty()) {
            log.error("Found an empty or null cookie.");
        }

        final String userId;
        try {
            userId = jwtService.extractUserId(updateProfileSession.getValue());
        } catch (ExpiredJwtException ex) {
            Claims claims = ex.getClaims();
            log.error("Profile update session expired: {}", claims.getSubject());
            throw new SessionExpiredException("Session expired.");
        } catch (Exception ex) {
            log.error("Invalid session token.");
            throw new IllegalJwtException("Invalid token.");
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userId, "no-password");

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        Returns true then above filter not executed and false then above filter executed.
        return !(request.getMethod().equals(HttpMethod.PUT.name()) &&
                request.getRequestURI().matches("/api/v1/users/profile"));
    }
}
