package com.ums.server.utils;

import com.ums.server.dtos.response.UserResponse;
import com.ums.server.models.UmsUsers;

public class UsersUtils {

    public static UserResponse buildUserResponse(UmsUsers user){
        return new UserResponse(
                user.getFirstName(),
                user.getLastName()
        );
    }
}
