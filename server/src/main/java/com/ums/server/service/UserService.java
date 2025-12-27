package com.ums.server.service;


import com.ums.server.exceptions.UserNotFoundException;
import com.ums.server.models.UmsUsers;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService  {


    UmsUsers loadUserByEmail(@NonNull String email) throws UserNotFoundException;

    UmsUsers loadUserById(@NonNull String userId) throws UsernameNotFoundException;
}
