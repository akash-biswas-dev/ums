package com.ums.server.repository;

import com.ums.server.models.Institution;
import com.ums.server.models.Role;
import com.ums.server.models.UmsUsers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test-db")
class RolePermissionRepositoryTest {

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private InstitutionRepository institutionRepository;

    @Autowired
    private UserRepository userRepository;


    @Nested
    class FetchAllPermissionsUserHaveWithInstitutions {

        @Test
        @Sql(scripts = "/sql/insert-users-with-role-permission-institutions.sql")
        void fetchAllThePermissionsUserHaveInstitutionWise() {
            long count = userRepository.count();
            assertTrue(count > 0);
        }
    }

}