package com.ums.server.repository;

import com.ums.server.dtos.db.RoleSystemPermissionDTO;
import com.ums.server.models.Role;
import com.ums.server.models.RoleSystemPermission;
import com.ums.server.models.permission.SystemPermissions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RepositoryTest
class RoleSystemPermissionRepositoryTest {

    @Autowired
    private RoleSystemPermissionRepository permissionRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private InstitutionRepository institutionRepository;


    @Nested
    class FindAllSystemPermissionsByRoleName {
        private Role testRole1;
        private Role testRole2;
        private Role testRole3;

        @BeforeEach
        void beforeEach() {
            this.testRole1 = roleRepository.save(new Role("Role 1", "A test user 2"));
            this.testRole2 = roleRepository.save(new Role("Role 2", "A test user 2"));
            this.testRole3 = roleRepository.save(new Role("Role 3", "A test user 2"));

            permissionRepository.save(new RoleSystemPermission(testRole1.getId(), SystemPermissions.TEST_SYSTEM_PERMISSIONS_1));
            permissionRepository.save(new RoleSystemPermission(testRole1.getId(), SystemPermissions.TEST_SYSTEM_PERMISSIONS_2));
            permissionRepository.save(new RoleSystemPermission(testRole2.getId(), SystemPermissions.TEST_SYSTEM_PERMISSIONS_2));
            permissionRepository.save(new RoleSystemPermission(testRole2.getId(), SystemPermissions.TEST_SYSTEM_PERMISSIONS_1));
            permissionRepository.save(new RoleSystemPermission(testRole2.getId(), SystemPermissions.TEST_SYSTEM_PERMISSIONS_3));
        }

        @Test
        void shouldHaveAllTheRoleSystemPermissionsSaved() {
            long entries = permissionRepository.count();
            assertEquals(5, entries);
        }


        @Test
        void shouldHaveReturnCorrectPermissionsARoleHas() {
            List<RoleSystemPermissionDTO> permissionForTestRole1 = permissionRepository.findAllPermissionsByRoleId(testRole1.getId());
            List<RoleSystemPermissionDTO> permissionsForTestRole2 = permissionRepository.findAllPermissionsByRoleId(testRole2.getId());
            List<RoleSystemPermissionDTO> permissionsForTestRole3 = permissionRepository.findAllPermissionsByRoleId(testRole3.getId());

            assertEquals(2, permissionForTestRole1.size());
            assertEquals(3, permissionsForTestRole2.size());
            assertEquals(0, permissionsForTestRole3.size());
        }
    }


}