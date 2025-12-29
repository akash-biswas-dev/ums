package com.ums.server.service.impl;

import com.ums.server.dtos.projections.InstitutionPermissionProjection;
import com.ums.server.dtos.projections.SystemPermissionProjection;
import com.ums.server.dtos.projections.RoleNameProjection;
import com.ums.server.exceptions.UserNotFoundException;
import com.ums.server.models.permission.InstitutionPermission;
import com.ums.server.models.permission.SystemPermissions;
import com.ums.server.models.UmsUsers;
import com.ums.server.repository.RolePermissionRepository;
import com.ums.server.repository.UserPermissionRepository;
import com.ums.server.repository.UserRepository;
import com.ums.server.repository.UserRoleRepository;
import com.ums.server.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserPermissionRepository userPermissionRepository;

    private final RolePermissionRepository rolePermissionRepository;

    private final UserRoleRepository userRoleRepository;

    @NonNull
    @Override
    public UmsUsers loadUserByEmail(@NonNull String email) throws UserNotFoundException {
        Optional<UmsUsers> userOptional = userRepository.findByEmailIgnoreCase(email);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(email);
        }
        return updateUserPermissions(userOptional.get());
    }

    @Override
    public UmsUsers loadUserById(@NonNull String userId) throws UsernameNotFoundException {

        Optional<UmsUsers> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(userId);
        }
        return updateUserPermissions(userOptional.get());
    }

    private UmsUsers updateUserPermissions(UmsUsers umsUsers) {

        String userId = umsUsers.getId();

        //      Fetch all the roles user have.
        List<RoleNameProjection> userRoles = userRoleRepository.findById_UserId(userId);

//      Use a hash set to have distinct permission otherwise will get duplicate permission.
        Set<SystemPermissions> permissions = new HashSet<>();

//       Collect all the permissions user have by role.
        Map<String, Set<InstitutionPermission>> institutionPermissions = new HashMap<>();

        for (RoleNameProjection roleNameProjection : userRoles) {

        }
//       Collect all user individual permissions.
        List<SystemPermissions> systemPermissions = userPermissionRepository
                .findById_UserId(userId)
                .stream()
                .map(SystemPermissionProjection::getId_permission)
                .toList();

        umsUsers.setPermissions(Set.copyOf(systemPermissions), institutionPermissions);
        return umsUsers;
    }
}
