package com.ums.server.service.impl;

import com.ums.server.exceptions.UserNotFoundException;
import com.ums.server.models.UmsUsers;
import com.ums.server.models.UserPermission;
import com.ums.server.repository.RolePermissionRepository;
import com.ums.server.repository.UserPermissionRepository;
import com.ums.server.repository.UserRepository;
import com.ums.server.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RolePermissionRepository rolePermissionRepository;

    private final UserPermissionRepository userPermissionRepository;

    @NonNull
    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        return userRepository.findByEmailIgnoreCase(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public UmsUsers getUserDetailsById(String userId) {

        return userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
