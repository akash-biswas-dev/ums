package com.ums.server.controller;


import com.ums.server.dtos.requests.UserProfileRequest;
import com.ums.server.dtos.response.UserResponse;
import com.ums.server.exceptions.InvalidAuthenticationException;
import com.ums.server.models.UmsUsers;
import com.ums.server.service.JwtService;
import com.ums.server.service.UserService;
import com.ums.server.utils.UsersUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/users")
public class UsersController {

    public static final String PROFILE_SESSION = "profile_update_session";

    private final UserService userService;

    private final JwtService jwtService;

    @PutMapping(value = "/profile")
    public ResponseEntity<UserResponse> updateProfile(
            @RequestBody UserProfileRequest profileRequest,
            Authentication authentication
    ) {
        String userId = (String) authentication.getPrincipal();

        UmsUsers user = userService.updateProfile(userId, profileRequest);

        UserResponse userResponse = UsersUtils.buildUserResponse(user);

        return new ResponseEntity<>(userResponse, HttpStatus.ACCEPTED);
    }

}
