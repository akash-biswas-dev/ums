package com.ums.server.repository;

import com.ums.server.dtos.db.RoleIdDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertEquals;


@RepositoryTest
@Sql(scripts = {
        "/test-data-sql/users.sql",
        "/test-data-sql/roles.sql",
        "/test-data-sql/repository/user-role-repository-test.sql"
})
class UserRoleRepositoryTest {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UmsUserRepository userRepository;

    private Map<String,List<String>> rolesUserHave;

    @BeforeEach
    void beforeEach(){
        this.rolesUserHave = new HashMap<>();

        rolesUserHave.put("550e8400-e29b-41d4-a716-446655440006",List.of(
                "660e8400-e29b-41d4-a716-446655440001",
                "660e8400-e29b-41d4-a716-446655440002",
                "660e8400-e29b-41d4-a716-446655440008"
        ));
        rolesUserHave.put("550e8400-e29b-41d4-a716-446655440007",List.of(
                "660e8400-e29b-41d4-a716-446655440002",
                "660e8400-e29b-41d4-a716-446655440001"
        ));
        rolesUserHave.put("550e8400-e29b-41d4-a716-446655440008",List.of(
                "660e8400-e29b-41d4-a716-446655440002"
        ));
    }


    @Test
    void shouldNotHaveAllTheUserRoleEntries() {
        AtomicLong expectedEntries = new AtomicLong(0);
        rolesUserHave.forEach((_userId,roles)-> expectedEntries.addAndGet(roles.size()));

        AtomicLong actualEntries= new AtomicLong(0);

        rolesUserHave.forEach((userId,_roles)-> {
            int countOfRolesUserHave = userRoleRepository.findAllRolesByUserId(userId).size();
            actualEntries.addAndGet(countOfRolesUserHave);
        });
        assertEquals(expectedEntries.get(),actualEntries.get());

    }

    @Test
    void shouldHaveSameNumberOfRolesUserHave(){
        rolesUserHave
                .keySet()
                .forEach((userId)->{
                    List<RoleIdDTO> rolesUserActuallyHave = userRoleRepository.findAllRolesByUserId(userId);
                    assertEquals(rolesUserHave.get(userId).size(),rolesUserActuallyHave.size());
                });
    }


}