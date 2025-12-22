package com.ums.server.controller;


import com.ums.server.config.SecurityConfig;
import com.ums.server.exceptions.JwtTokenExpiredException;
import com.ums.server.filters.FilterChainExceptionHandler;
import com.ums.server.filters.JwtAuthenticationFilter;
import com.ums.server.service.JwtService;
import com.ums.server.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SecureRestEndpoint.class)
@Import({SecurityConfig.class, JwtAuthenticationFilter.class, FilterChainExceptionHandler.class})
class SecuredRestEndpointTest {

    private final static String BASE_URL = "/api/v1/secured";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private PasswordEncoder passwordEncoder;


    @Test
    void shouldGet401WhenTryToAccessSecuredEndpoint() throws Exception {
        mockMvc
                .perform(get(BASE_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldSendRedirectWhenTheTokenIsExpired() throws Exception {
        when(jwtService.extractAuthentication(any())).thenThrow(new JwtTokenExpiredException("Token has expired"));

        mockMvc.perform(get(BASE_URL)
                        .cookie(new Cookie("token", "a-long-token")))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/auth"));
    }

    @Test
    void shouldGet403WhenAccessSecuredEndpointWithoutEnoughPermission() throws Exception {

        UserDetails user = User.builder()
                .username("user")
                .password("password")
                .authorities(List.of())
                .build();

        when(jwtService.extractAuthentication(any())).thenReturn(user);
        mockMvc.perform(get(BASE_URL + "/permission")
                        .cookie(new Cookie("token", "a-long-token")))
                .andExpect(status().isForbidden());
    }

}