package com.ums.server.repository;

import com.ums.server.dtos.db.RoleSystemPermissionDTO;
import com.ums.server.models.Role;
import com.ums.server.models.RoleSystemPermission;
import com.ums.server.models.permission.SystemPermissions;
import org.junit.jupiter.api.BeforeEach;
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


    private Role testRole1;
    private Role testRole2;

   @BeforeEach
    void beforeEach() {
        this.testRole1 = roleRepository.save(new Role("Role 1", "A test user 2"));
        this.testRole2 = roleRepository.save(new Role("Role 2", "A test user 2"));

        permissionRepository.save(new RoleSystemPermission(testRole1.getId(), SystemPermissions.PROGRAM_READ));
        permissionRepository.save(new RoleSystemPermission(testRole1.getId(), SystemPermissions.PROGRAM_UPDATE));
        permissionRepository.save(new RoleSystemPermission(testRole2.getId(), SystemPermissions.PROGRAM_UPDATE));
        permissionRepository.save(new RoleSystemPermission(testRole2.getId(), SystemPermissions.PROGRAM_READ));
        permissionRepository.save(new RoleSystemPermission(testRole2.getId(), SystemPermissions.PROGRAM_DELETE));
    }

    @Test
    void shouldHaveAllTheRoleSystemPermissionsSaved() {
        long entries = permissionRepository.count();
        assertEquals(5,entries);
    }


    @Test
    void shouldHaveReturnCorrectPermissionsARoleHas(){
        List<RoleSystemPermissionDTO> permissionForTestRole1 = permissionRepository.findAllPermissionsByRoleId(testRole1.getId());
        List<RoleSystemPermissionDTO> permissionsForTestRole2 = permissionRepository.findAllPermissionsByRoleId(testRole2.getId());

        assertEquals(2, permissionForTestRole1.size());
        assertEquals(3, permissionsForTestRole2.size());
    }

}