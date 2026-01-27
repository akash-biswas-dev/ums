package com.ums.server.repository;


import com.ums.server.models.Institution;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RepositoryTest
@Sql(scripts = {
        "/test-data-sql/base-test-data.sql",
        "/test-data-sql/repository/role-institution-permission-repository-test.sql"
})
class RoleInstitutePermissionRepositoryTest {

    @Autowired
    private RoleInstitutePermissionRepository permissionRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UmsUserRepository userRepository;

    @Autowired
    private InstitutionRepository institutionRepository;

    @Test
    void shouldHaveAllData() {
        List<Institution> institutions = institutionRepository.findAll();
        assertFalse(institutions.isEmpty());
//        assertTrue(roleRepository.);
    }

}

