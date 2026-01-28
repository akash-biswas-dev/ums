package com.ums.server.service;

import com.ums.server.dtos.requests.UserCredentials;
import com.ums.server.models.UmsUsers;

public interface AuthService {
    UmsUsers authenticate(UserCredentials userCredentials);
}
