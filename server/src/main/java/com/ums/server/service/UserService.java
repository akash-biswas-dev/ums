package com.ums.server.service;


import com.ums.server.dtos.requests.UserProfileRequest;
import com.ums.server.dtos.response.UserResponse;
import com.ums.server.exceptions.UserNotFoundException;
import com.ums.server.models.UmsUsers;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService  {

    UmsUsers getUserByEmail(@NonNull String email) throws UserNotFoundException;

    UmsUsers getUserById(@NonNull String userId) throws UserNotFoundException;

    UmsUsers updateProfile(String userId, UserProfileRequest profileRequest) throws IllegalAccessException;


}
