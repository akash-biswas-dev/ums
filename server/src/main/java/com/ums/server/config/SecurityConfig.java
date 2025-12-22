package com.ums.server.config;

import com.ums.server.filters.FilterChainExceptionHandler;
import com.ums.server.filters.JwtAuthenticationFilter;
import com.ums.server.filters.RefreshTokenAuthenticationFilter;
import com.ums.server.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final RefreshTokenAuthenticationFilter refreshTokenFilter;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    private static final String[] WHITELIST = {
            "/",
            "/ums/**",
            "/index.html",
            "/about",
            "/auth/**",
            "/css/**",
            "/js/**",
            "/images/**",
            "/assets/**",
            "/vite.svg",
            "/favicon.ico",
    };

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) {

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return http
                .httpBasic(HttpBasicConfigurer::disable)
                .csrf(CsrfConfigurer::disable)
                .cors(CorsConfigurer::disable)
                .authenticationProvider(authenticationProvider)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(exceptionHandler, LogoutFilter.class)
                .addFilterBefore(refreshTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorize -> {
                    authorize
                            .requestMatchers(WHITELIST)
                            .permitAll()
                            .anyRequest().authenticated();
                })
                .exceptionHandling(exception -> {
                    exception.authenticationEntryPoint((req, resp, e) -> {
                        log.error("Exception occurred while authenticating the user: {} at path {}",e.getMessage(),req.getRequestURI());
                        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    });
                })
                .build();
    }
}
