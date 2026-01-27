package com.ums.server.repository;


import com.ums.server.models.Institution;
import com.ums.server.models.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Sql(scripts = {
        "/test-data-sql/role-institution-permission-repository-test.sql"
})
@RepositoryTest
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
        assertTrue();
//        assertTrue(roleRepository.);
    }

}

