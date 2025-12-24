package com.ums.server.service;

import com.ums.server.dtos.JwtCookie;
import com.ums.server.dtos.requests.UserCredentials;
import com.ums.server.dtos.response.AuthToken;

public interface AuthService {
    JwtCookie generateJwtCookie(UserCredentials userCredentials, Boolean rememberMe);

    AuthToken generateAuthTokens(String userId);
}
