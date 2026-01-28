package com.ums.server.repository;


import com.ums.server.dtos.db.InstitutionPermissionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RepositoryTest
@Sql(scripts = {
        "/test-data-sql/roles.sql",
        "/test-data-sql/institutions.sql",
        "/test-data-sql/repository/role-institution-permission-repository-test.sql"
})
class RoleInstitutionPermissionRepositoryTest {

    @Autowired
    private RoleInstitutionPermissionRepository institutePermissionRepository;

    @Test
    void shouldHaveSomeData() {
        long entries = institutePermissionRepository.count();
        assertEquals(5, entries);
    }

    @Nested
    class FindAllInstitutionPermissionsRoleHave{

        private String testSystemAdminRoleId ;
        private String testExamControllerRoleId ;

        @BeforeEach
        void beforeEach(){
           this.testSystemAdminRoleId=  "660e8400-e29b-41d4-a716-446655440001";
           this.testExamControllerRoleId = "660e8400-e29b-41d4-a716-446655440002";
        }

        @Test
        void shouldHaveFindAllTheInstitutionPermissionARoleHave(){
            List<InstitutionPermissionDTO> institutePermissionSystemAdminRoleHave = institutePermissionRepository.findAllPermissionsByRoleId(testSystemAdminRoleId);
            List<InstitutionPermissionDTO> institutionPermissionExamControllerRoleHave = institutePermissionRepository.findAllPermissionsByRoleId(testExamControllerRoleId);

            assertEquals(3,institutePermissionSystemAdminRoleHave.size());
            assertEquals(2,institutionPermissionExamControllerRoleHave.size());
        }
    }

}

