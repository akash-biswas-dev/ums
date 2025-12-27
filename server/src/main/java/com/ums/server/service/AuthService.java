package com.ums.server.service;

import com.ums.server.dtos.JwtAuthorization;
import com.ums.server.dtos.requests.UserCredentials;
import com.ums.server.dtos.response.Authorization;

public interface AuthService {
    JwtAuthorization generateJwtCookie(UserCredentials userCredentials, Boolean rememberMe);

    Authorization generateAuthTokens(String userId);
}
