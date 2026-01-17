package com.ums.server.service.impl;

import com.ums.server.dtos.db.InstitutionPermissionDTO;
import com.ums.server.dtos.db.RoleIdDTO;
import com.ums.server.dtos.projections.RoleNameProjection;
import com.ums.server.dtos.db.RoleSystemPermissionDTO;
import com.ums.server.exceptions.UserNotFoundException;
import com.ums.server.models.UmsUsers;
import com.ums.server.models.permission.InstitutionPermission;
import com.ums.server.models.permission.SystemPermissions;
import com.ums.server.repository.RoleInstitutePermissionRepository;
import com.ums.server.repository.RoleSystemPermissionRepository;
import com.ums.server.repository.UserRepository;
import com.ums.server.repository.UserRoleRepository;
import com.ums.server.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleInstitutePermissionRepository rolePermissionRepository;

    private final UserRoleRepository userRoleRepository;

    private final RoleSystemPermissionRepository systemPermissionRepository;

    private final RoleInstitutePermissionRepository institutePermissionRepository;

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
//        Fetch all the roles user have.
        List<RoleIdDTO> roleIds = userRoleRepository
                .findAllRolesByUserId(userId);

//       Fetch all the system permission user have.
        Set<SystemPermissions> systemPermissions = new HashSet<>();

        for (RoleIdDTO roleId : roleIds) {
//            Fetch all permissions for each role and add them in the collection.
            List<SystemPermissions> roleSystemPermissions =
                    systemPermissionRepository
                            .findAllPermissionsByRoleId(roleId.roleId())
                            .stream()
                            .map(RoleSystemPermissionDTO::systemPermission)
                            .toList();
            systemPermissions.addAll(roleSystemPermissions);
        }

//        Fetch all the institution permissions user have and add in the institution wise.

        Map<String, Set<InstitutionPermission>> institutionPermissions = new HashMap<>();

        for (RoleIdDTO roleId : roleIds) {
            institutePermissionRepository
                    .findAllPermissionsByRoleId(roleId.roleId())
                    .forEach(institutionPermissionDTO -> {
                        String institutionCode = institutionPermissionDTO.institutionCode();

                        if (!institutionPermissions.containsKey(institutionCode)) {
                            institutionPermissions.put(institutionCode, new HashSet<>());
                        }
                        institutionPermissions
                                .get(institutionCode)
                                .add(institutionPermissionDTO.institutionPermission());
                    });
        }

        umsUsers.setPermissions(systemPermissions, institutionPermissions);
        return umsUsers;
    }
}
