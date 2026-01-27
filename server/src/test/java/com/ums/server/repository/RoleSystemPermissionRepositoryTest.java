package com.ums.server.repository;

import com.ums.server.models.permission.SystemPermissions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


@RepositoryTest
@Sql(scripts = {
        "/test-data-sql/base-test-data.sql",
        "/test-data-sql/repository/role-system-permission-test.sql"
})
class RoleSystemPermissionRepositoryTest {

    @Autowired
    private RoleSystemPermissionRepository permissionRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private InstitutionRepository institutionRepository;

    private Map<String, List<SystemPermissions>> systemPermissionsUserHave;

    @BeforeEach
    void beforeEach() {
        this.systemPermissionsUserHave = new HashMap<>();

        systemPermissionsUserHave.put("660e8400-e29b-41d4-a716-446655440001",List.of(
                SystemPermissions.TEST_SYSTEM_PERMISSION_1,
                SystemPermissions.TEST_SYSTEM_PERMISSION_2,
                SystemPermissions.TEST_SYSTEM_PERMISSION_3
        ));
        systemPermissionsUserHave.put("660e8400-e29b-41d4-a716-446655440002",List.of(
                SystemPermissions.TEST_SYSTEM_PERMISSION_3,
                SystemPermissions.TEST_SYSTEM_PERMISSION_1
        ));
        systemPermissionsUserHave.put("660e8400-e29b-41d4-a716-446655440008",List.of());
    }

    @Test
    void shouldHaveAllTheRoleSystemPermissionsSaved() {
        long entries = permissionRepository.count();
        assertEquals(5, entries);
    }

    @Test
    void shouldHaveReturnCorrectPermissionsARoleHas() {
        systemPermissionsUserHave.forEach((roleId,permissionList)->{
            int permissionCountExpected = permissionRepository.findAllPermissionsByRoleId(roleId).size();
            assertEquals(permissionCountExpected,permissionList.size());
        });
    }


}