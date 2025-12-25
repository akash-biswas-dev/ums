package com.ums.server.service.impl;

import com.ums.server.dtos.projections.PermissionProjection;
import com.ums.server.dtos.projections.RoleNameProjection;
import com.ums.server.exceptions.UserNotFoundException;
import com.ums.server.models.RolePermission;
import com.ums.server.models.UmsPermissions;
import com.ums.server.models.UmsUsers;
import com.ums.server.models.UserPermission;
import com.ums.server.repository.RolePermissionRepository;
import com.ums.server.repository.UserPermissionRepository;
import com.ums.server.repository.UserRepository;
import com.ums.server.repository.UserRoleRepository;
import com.ums.server.service.JwtService;
import com.ums.server.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserPermissionRepository userPermissionRepository;

    private final RolePermissionRepository rolePermissionRepository;

    private final UserRoleRepository userRoleRepository;

    @NonNull
    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        return userRepository.findByEmailIgnoreCase(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public UmsUsers getUmsUserWithPermissions(String userId) {

        Optional<UmsUsers> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(userId);
        }
//      Fetch all the roles user have.
        List<RoleNameProjection> userRoles = userRoleRepository.findById_UserId(userId);

//      Use a hash set to have distinct permission otherwise will get duplicate permission.
        Set<UmsPermissions> permissions = new HashSet<>();

//       Collect all the permissions user have by role.
        for (RoleNameProjection roleNameProjection : userRoles) {
            List<UmsPermissions> rolePermission = rolePermissionRepository
                    .findById_RoleName(roleNameProjection.getId_RoleName())
                    .stream()
                    .map(PermissionProjection::getId_permission)
                    .toList();
            permissions.addAll(rolePermission);
        }

//       Collect all user individual permissions.
        List<UmsPermissions> userPermissions = userPermissionRepository
                .findById_StuffId(userId)
                .stream()
                .map(PermissionProjection::getId_permission)
                .toList();

        permissions.addAll(userPermissions);

        UmsUsers umsUsers = userOptional.get();

        umsUsers.setAuthorities(permissions);

        return umsUsers;
    }
}
