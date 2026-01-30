package com.ums.server.config;

import com.ums.server.exceptions.InvalidAuthenticationException;
import com.ums.server.filters.FilterChainExceptionHandler;
import com.ums.server.filters.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final FilterChainExceptionHandler exceptionHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private static final String[] WHITELIST = {
            "/index.html",
            "/ums/**",
            "/auth/**",
            "/update-profile",
            "/assets/**",
            "/vite.svg"
    };

    private static final String[] WHITELIST_API_ENDPOINTS = {
            "/api/v1/auth",
            "/api/v1/users/**"
    };

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) {

        return http
                .httpBasic(HttpBasicConfigurer::disable)
                .csrf(CsrfConfigurer::disable)
                .cors(CorsConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(exceptionHandler, LogoutFilter.class)
                .addFilterAt(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorize -> {
                    authorize
                            .requestMatchers(WHITELIST)
                            .permitAll()
                            .requestMatchers(WHITELIST_API_ENDPOINTS)
                            .permitAll()
                            .anyRequest()
                            .authenticated();
                })
                .exceptionHandling(exception -> {
                    exception.authenticationEntryPoint((req, resp, e) -> {
//                        This exception occurred when a user try to access a secured path without Authentication.
                        log.error("Exception occurred while authenticating the user: {} at path {}", e.getMessage(), req.getRequestURI());
                        throw new InvalidAuthenticationException("Invalid authentication attempt");
                    });
                })
                .logout(logout -> logout
                        .logoutUrl("/api/v1/logout")
                        .deleteCookies("session")
                        .logoutSuccessUrl("/auth"))
                .build();
    }
}
