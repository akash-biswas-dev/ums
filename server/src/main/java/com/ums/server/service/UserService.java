package com.ums.server.service;


import com.ums.server.models.UmsUsers;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UmsUsers getUmsUserWithPermissionsById(String userId);
}
