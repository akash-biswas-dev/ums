package com.ums.server.controller;

import com.ums.server.config.SecurityConfig;
import com.ums.server.dtos.response.Authorization;
import com.ums.server.dtos.response.UserResponse;
import com.ums.server.filters.FilterChainExceptionHandler;
import com.ums.server.filters.RefreshAuthorizationFilter;
import com.ums.server.service.AuthService;
import com.ums.server.service.JwtService;
import com.ums.server.service.UserService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(value = AuthorizationController.class)
@Import({SecurityConfig.class, FilterChainExceptionHandler.class, RefreshAuthorizationFilter.class})
class AuthorizationControllerTest {

    private static final String BASE_URL = "/api/v1/authorize";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @Test
    void generateTokenWhenSessionCookiePresent() throws Exception {
        String token = "a-long-token";
        String userId = "user-id";
        String authorization = "access-token";

        UserResponse userResponse = new UserResponse(
                "Akash",
                "Biswas"
        );


        when(jwtService.extractUserId(token)).thenReturn(userId);
        when(authService.generateAuthTokens(userId)).thenReturn(new Authorization(authorization, userResponse));

        mockMvc.perform(post(BASE_URL).cookie(new Cookie("session", token)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.authorization").value(authorization))
                .andExpect(jsonPath("$.user.firstName").value(userResponse.firstName()))
                .andExpect(jsonPath("$.user.lastName").value(userResponse.lastName()));

    }

}