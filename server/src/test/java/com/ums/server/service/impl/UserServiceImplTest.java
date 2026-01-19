package com.ums.server.service.impl;

import com.ums.server.models.Gender;
import com.ums.server.models.UmsUsers;
import com.ums.server.repository.RoleInstitutePermissionRepository;
import com.ums.server.repository.RoleSystemPermissionRepository;
import com.ums.server.repository.UserRepository;
import com.ums.server.repository.UserRoleRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.UUID;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private RoleInstitutePermissionRepository rolePermissionRepository;

    @Mock
    private RoleSystemPermissionRepository systemPermissionRepository;

    @Mock
    private RoleInstitutePermissionRepository institutePermissionRepository;

    private final UmsUsers user = UmsUsers.builder()
            .id(UUID.randomUUID().toString())
            .email("umsuser@gmail.com")
            .password("123456")
            .gender(Gender.MALE)
            .joinedOn(LocalDate.now())
            .isLocked(false)
            .isEnabled(true)
            .build();

    @Nested
    class GetUmsUserWithPermissionsById {

    }
}