package com.ums.server.service.impl;

import com.ums.server.models.Gender;
import com.ums.server.models.UmsUsers;
import com.ums.server.repository.RoleInstitutionPermissionRepository;
import com.ums.server.repository.RoleSystemPermissionRepository;
import com.ums.server.repository.UmsUserRepository;
import com.ums.server.repository.UserRoleRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UmsUserRepository userRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private RoleInstitutionPermissionRepository rolePermissionRepository;

    @Mock
    private RoleSystemPermissionRepository systemPermissionRepository;

    @Mock
    private RoleInstitutionPermissionRepository institutePermissionRepository;

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

    @Test
    void someTest(){
        Field[] fields = UmsUsers.class.getDeclaredFields();

        System.out.println(fields.length);
        for (Field f :fields){
            System.out.println(f.getName());
        }

    }
}