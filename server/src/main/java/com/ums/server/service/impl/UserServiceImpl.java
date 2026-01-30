package com.ums.server.service.impl;

import com.ums.server.dtos.db.RoleIdDTO;
import com.ums.server.dtos.db.RoleSystemPermissionDTO;
import com.ums.server.dtos.requests.UserProfileRequest;
import com.ums.server.exceptions.ServiceUnavailableException;
import com.ums.server.exceptions.UserNotFoundException;
import com.ums.server.models.UmsUsers;
import com.ums.server.models.permission.InstitutionPermission;
import com.ums.server.models.permission.SystemPermissions;
import com.ums.server.repository.RoleInstitutionPermissionRepository;
import com.ums.server.repository.RoleSystemPermissionRepository;
import com.ums.server.repository.UmsUserRepository;
import com.ums.server.repository.UserRoleRepository;
import com.ums.server.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UmsUserRepository userRepository;

    private final RoleInstitutionPermissionRepository rolePermissionRepository;

    private final UserRoleRepository userRoleRepository;

    private final RoleSystemPermissionRepository systemPermissionRepository;

    private final RoleInstitutionPermissionRepository institutePermissionRepository;

    @NonNull
    @Override
    public UmsUsers getUserByEmail(@NonNull String email) throws UserNotFoundException {
        Optional<UmsUsers> userOptional = userRepository.findByEmailIgnoreCase(email);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(email);
        }
        return updateUserPermissions(userOptional.get());
    }

    @Override
    public UmsUsers getUserById(@NonNull String userId) throws UserNotFoundException{

        Optional<UmsUsers> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(userId);
        }
        return updateUserPermissions(userOptional.get());
    }

    @Override
    public UmsUsers updateProfile(String userId, UserProfileRequest profileRequest) throws IllegalAccessException {
        /*Optional<UmsUsers> usersOptional = userRepository.findById(userId);

        if(usersOptional.isEmpty()){
            log.error("Invalid User found while updating profile with id: {}", userId);
            throw new ServiceUnavailableException("Try to contact the administrator.");
        }

        UmsUsers user = usersOptional.get();
*/

        Field[] profileFields = UserProfileRequest.class.getDeclaredFields();

        for (Field filed : profileFields){
            String profileRequestValue = filed.get(profileRequest);
            filed.set(user,);
        }

        for (Field f : fields){
            System.out.println(f);
        }

        return null;
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
//                       If found a new Institution code then add a new HashSet.
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
