package com.ums.server.controller;


import com.ums.server.config.SecurityConfig;
import com.ums.server.dtos.ErrorCode;
import com.ums.server.exceptions.JwtTokenExpiredException;
import com.ums.server.filters.FilterChainExceptionHandler;
import com.ums.server.filters.JwtAuthenticationFilter;
import com.ums.server.service.JwtService;
import com.ums.server.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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


    private String token;

    private UserDetails user;

    @BeforeEach
    void beforeEach() {
        this.token = "a-long-token";
        this.user = User.builder()
                .username("user")
                .password("password")
                .authorities(List.of())
                .build();
    }

    @Test
    void shouldGet401WhenTryToAccessSecuredEndpoint() throws Exception {
        mockMvc
                .perform(get(BASE_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldSendRedirectWhenTheTokenIsExpired() throws Exception {

        String token = "a-long-token";
        when(jwtService.extractAuthentication(token)).thenThrow(new JwtTokenExpiredException("Token has expired"));

        mockMvc.perform(get(BASE_URL).header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value(ErrorCode.TOKEN_EXPIRED.name()));

    }

    @Test
    void shouldGet403WhenAccessSecuredEndpointWithoutEnoughPermission() throws Exception {


        when(jwtService.extractAuthentication(token)).thenReturn(user);

        mockMvc.perform(get(BASE_URL + "/permission")
                        .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token)))
                .andExpect(status().isForbidden());
    }


    @Test
    void shouldGetErrorAsUnknownCauseWhenAnUnhandledExceptionIsThrown() throws Exception {
        when(jwtService.extractAuthentication(token)).thenReturn(user);

        mockMvc.perform(get(BASE_URL + "/error")
                        .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token)))
                .andExpect(jsonPath("$.error").value(ErrorCode.UNKNOWN_CAUSE.name()));
    }

}