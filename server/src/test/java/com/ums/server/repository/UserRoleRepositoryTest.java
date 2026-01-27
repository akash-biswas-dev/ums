package com.ums.server.repository;

import com.ums.server.dtos.db.RoleIdDTO;
import com.ums.server.models.Role;
import com.ums.server.models.UmsUsers;
import com.ums.server.models.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;


@RepositoryTest
class UserRoleRepositoryTest {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UmsUserRepository userRepository;


    private Map<String, List<String>> rolesUserHave;

    @BeforeEach
    void beforeEach() {
        this.rolesUserHave = new HashMap<>();
        Role testRole1 = roleRepository.save(new Role("Role 1", "A test user 2"));
        Role testRole2 = roleRepository.save(new Role("Role 2", "A test user 2"));
        Role testRole3 = roleRepository.save(new Role("Role 3", "A test user 2"));

        UmsUsers testUser1 = userRepository.save(new UmsUsers(
                "testUser1@gmail.com",
                "password",
                false,
                true,
                false
        ));
        UmsUsers testUser2 = userRepository.save(new UmsUsers(
                "testUser2@gmail.com",
                "password",
                false,
                true,
                false
        ));

        rolesUserHave.put(testUser1.getId(), List.of(testRole1.getId(), testRole2.getId(), testRole3.getId()));
        rolesUserHave.put(testUser2.getId(), List.of(testRole1.getId(), testRole3.getId()));

        rolesUserHave.forEach((userId, roles) -> {
            List<UserRole> userRoles = new ArrayList<>();
            for (String roleId : roles) {
                userRoles.add(new UserRole(userId, roleId));
            }
            userRoleRepository.saveAll(userRoles);
        });
    }

    @Test
    void shouldNotHaveAllTheUserRoleEntries() {
        AtomicLong expectedEntries = new AtomicLong(0);

        rolesUserHave.forEach((_userId,roles)->expectedEntries.addAndGet(roles.size()));

        long actual = userRoleRepository.count();
        assertEquals(expectedEntries.get(),actual);

    }
    @Nested
    class FindAllRolesByUserId{
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
}