package com.ums.server.service.impl;

import com.ums.server.dtos.projections.PermissionProjection;
import com.ums.server.dtos.projections.RoleNameProjection;
import com.ums.server.models.Gender;
import com.ums.server.models.UmsPermissions;
import com.ums.server.models.UmsUsers;
import com.ums.server.models.UserRole;
import com.ums.server.repository.RolePermissionRepository;
import com.ums.server.repository.UserPermissionRepository;
import com.ums.server.repository.UserRepository;
import com.ums.server.repository.UserRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private RolePermissionRepository rolePermissionRepository;

    @Mock
    private UserPermissionRepository userPermissionRepository;

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

        private UserServiceImpl userServiceImpl;


        @BeforeEach
        void setUp() {
            this.userServiceImpl = new UserServiceImpl(
                    userRepository,
                    userPermissionRepository,
                    rolePermissionRepository,
                    userRoleRepository
            );
        }

        @Test
        void haveAllThePermissionsGettingThroughRole() {

            UserRole admin = new UserRole("Admin", user.getId(), LocalDate.now());
            UserRole director = new UserRole("Director", user.getId(), LocalDate.now());

            List<RoleNameProjection> roleNameProjectionsList = Stream.of(admin, director)
                    .map(userRole -> (RoleNameProjection) userRole::getRoleName)
                    .toList();

            List<UmsPermissions> adminPermissions = List.of(
                    UmsPermissions.ADMIN_WRITE,
                    UmsPermissions.ADMIN_READ,
                    UmsPermissions.DIRECTOR_READ,
                    UmsPermissions.DIRECTOR_WRITE
            );
            List<UmsPermissions> directorPermission = List.of(
                    UmsPermissions.DIRECTOR_READ,
                    UmsPermissions.DIRECTOR_WRITE
            );

            when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
            when(userRoleRepository.findById_UserId(user.getId())).thenReturn(roleNameProjectionsList);

            when(rolePermissionRepository.findById_RoleName(admin.getRoleName())).thenReturn(getPermissionProjection(adminPermissions));
            when(rolePermissionRepository.findById_RoleName(director.getRoleName())).thenReturn(getPermissionProjection(directorPermission));

            when(userPermissionRepository.findById_StuffId(user.getId())).thenReturn(List.of());

            UmsUsers users = userServiceImpl.getUmsUserWithPermissions(user.getId());

            Set<UmsPermissions> expectedPermissions = new HashSet<>();

            expectedPermissions.addAll(adminPermissions);
            expectedPermissions.addAll(directorPermission);

            assertEquals(expectedPermissions.size(), users.getAuthorities().size());
        }

        @Test
        void userHaveAllIndividualPermission() {

            List<UmsPermissions> permissions = List.of(
                    UmsPermissions.ADMIN_WRITE,
                    UmsPermissions.ADMIN_READ,
                    UmsPermissions.DIRECTOR_READ,
                    UmsPermissions.DIRECTOR_WRITE
            );

            when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

            when(userRoleRepository.findById_UserId(user.getId())).thenReturn(List.of());

            when(userPermissionRepository.findById_StuffId(user.getId())).thenReturn(getPermissionProjection(permissions));

            UmsUsers users = userServiceImpl.getUmsUserWithPermissions(user.getId());

            assertEquals(users.getAuthorities().size(), permissions.size());
        }

        @Test
        void userDistinctPermissionsWhenHaveSamePermissionThroughRolePermissionAndIndividualPermission() {

            UserRole admin = new UserRole("Admin", user.getId(), LocalDate.now());

            List<RoleNameProjection> roleNameProjectionsList = Stream.of(admin)
                    .map(userRole -> (RoleNameProjection) userRole::getRoleName)
                    .toList();

            List<UmsPermissions> rolePermissions = List.of(
                    UmsPermissions.ADMIN_WRITE,
                    UmsPermissions.ADMIN_READ,
                    UmsPermissions.DIRECTOR_READ,
                    UmsPermissions.DIRECTOR_WRITE
            );

            List<UmsPermissions> userPermissions = List.of(
                    UmsPermissions.DIRECTOR_READ,
                    UmsPermissions.STUDENT_UPDATE
            );

            when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
            when(userRoleRepository.findById_UserId(user.getId())).thenReturn(roleNameProjectionsList);

            when(rolePermissionRepository.findById_RoleName(admin.getRoleName())).thenReturn(getPermissionProjection(rolePermissions));

            when(userPermissionRepository.findById_StuffId(user.getId())).thenReturn(getPermissionProjection(userPermissions));

            Set<UmsPermissions> expectedPermissions = new HashSet<>();

            expectedPermissions.addAll(rolePermissions);
            expectedPermissions.addAll(userPermissions);

            UmsUsers users = userServiceImpl.getUmsUserWithPermissions(user.getId());

            assertEquals(expectedPermissions.size(), users.getAuthorities().size());

        }

        @Test
        void userHaveZeroPermissionsWhenAssignedNoIndividualPermissionAndRole() {

            when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

            when(userRoleRepository.findById_UserId(user.getId())).thenReturn(List.of());

            when(userPermissionRepository.findById_StuffId(user.getId())).thenReturn(List.of());

            UmsUsers users = userServiceImpl.getUmsUserWithPermissions(user.getId());

            assertEquals(0, users.getAuthorities().size());
        }

        private List<PermissionProjection> getPermissionProjection(List<UmsPermissions> permissions) {
            return permissions
                    .stream()
                    .map((permission) -> (PermissionProjection) () -> permission)
                    .toList();
        }


    }
}