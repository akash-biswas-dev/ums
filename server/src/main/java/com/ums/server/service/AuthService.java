package com.ums.server.service;

import com.ums.server.dtos.JwtCookie;
import com.ums.server.dtos.requests.UserCredentials;

public interface AuthService {
    JwtCookie generateJwtCookie(UserCredentials userCredentials, Boolean rememberMe);
}
