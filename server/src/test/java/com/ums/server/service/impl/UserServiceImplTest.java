package com.ums.server.service.impl;

import com.ums.server.dtos.db.InstitutionPermissionDTO;
import com.ums.server.dtos.db.RoleIdDTO;
import com.ums.server.dtos.db.RoleSystemPermissionDTO;
import com.ums.server.models.Gender;
import com.ums.server.models.UmsUsers;
import com.ums.server.models.permission.InstitutionPermission;
import com.ums.server.models.permission.SystemPermissions;
import com.ums.server.repository.*;
import com.ums.server.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UmsUserRepository userRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private RoleInstitutionPermissionRepository rolePermissionRepository;

    @Mock
    private RoleSystemPermissionRepository systemPermissionRepository;

    @Mock
    private RoleInstitutionPermissionRepository institutePermissionRepository;


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
        private UserService userService;

        private final String roleId1 = "role-id-1";
        private final String roleId2 = "role-id-2";


        private final String institution1 = "institution-1";
        private final String institution2 = "institution-2";

        private Map<String, List<RoleSystemPermissionDTO>> systemPermissions;

        private Map<String, List<InstitutionPermissionDTO>> institutePermission;

        private List<RoleIdDTO> roleIdDTOS;


        @BeforeEach
        void beforeEach() {
            this.roleIdDTOS = List.of(
                    new RoleIdDTO(roleId1),
                    new RoleIdDTO(roleId2)
            );

            this.institutePermission = Map.of(
                    roleId1, List.of(new InstitutionPermissionDTO(
                                    institution1,
                                    InstitutionPermission.TEST_INSTITUTION_PERMISSION_1
                            ),
                            new InstitutionPermissionDTO(
                                    institution1,
                                    InstitutionPermission.TEST_INSTITUTION_PERMISSION_2
                            ),
                            new InstitutionPermissionDTO(
                                    institution2,
                                    InstitutionPermission.TEST_INSTITUTION_PERMISSION_3
                            ))
                    , roleId2, List.of(
                            new InstitutionPermissionDTO(
                                    institution1,
                                    InstitutionPermission.TEST_INSTITUTION_PERMISSION_3
                            ),
                            new InstitutionPermissionDTO(
                                    institution2,
                                    InstitutionPermission.TEST_INSTITUTION_PERMISSION_1
                            ),
                            new InstitutionPermissionDTO(
                                    institution1,
                                    InstitutionPermission.TEST_INSTITUTION_PERMISSION_5
                            )
                    )
            );

            this.systemPermissions = Map.of(
                    roleId1, List.of(
                            new RoleSystemPermissionDTO(SystemPermissions.TEST_SYSTEM_PERMISSION_1),
                            new RoleSystemPermissionDTO(SystemPermissions.TEST_SYSTEM_PERMISSION_4),
                            new RoleSystemPermissionDTO(SystemPermissions.TEST_SYSTEM_PERMISSION_3),
                            new RoleSystemPermissionDTO(SystemPermissions.TEST_SYSTEM_PERMISSION_4)
                    ),
                    roleId2, List.of(
                            new RoleSystemPermissionDTO(SystemPermissions.TEST_SYSTEM_PERMISSION_4),
                            new RoleSystemPermissionDTO(SystemPermissions.TEST_SYSTEM_PERMISSION_2),
                            new RoleSystemPermissionDTO(SystemPermissions.TEST_SYSTEM_PERMISSION_5)
                    )
            );

            this.userService = new UserServiceImpl(
                    userRepository,
                    addressRepository,
                    rolePermissionRepository,
                    userRoleRepository,
                    systemPermissionRepository,
                    institutePermissionRepository
            );
        }

        @Test
        void userShouldHaveNoSystemAndInstitutePermission() {

            when(userRepository.findByEmailIgnoreCase(user.getEmail())).thenReturn(Optional.of(user));
            when(userRoleRepository.findAllRolesByUserId(user.getId())).thenReturn(roleIdDTOS);
//            Mock the calls made to fetch the system permissions by roleId.
            when(systemPermissionRepository.findAllPermissionsByRoleId(roleId1)).thenReturn(this.systemPermissions.get(this.roleId1));
            when(systemPermissionRepository.findAllPermissionsByRoleId(roleId2)).thenReturn(this.systemPermissions.get(this.roleId2));
//            Mock calls to fetch institution permissions.
            when(institutePermissionRepository.findAllPermissionsByRoleId(roleId1)).thenReturn(institutePermission.get(roleId1));
            when(institutePermissionRepository.findAllPermissionsByRoleId(roleId2)).thenReturn(institutePermission.get(roleId2));

            UmsUsers actualUser = userService.getUserByEmail(user.getEmail());

            assertEquals(user.getEmail(), actualUser.getEmail());
//            Match the count of system permission user actual have and should have.
            Set<SystemPermissions> actualSystemPermission = user.getSystemPermissions();

           Set<SystemPermissions> expectedSystemPermission = new HashSet<>();
            systemPermissions.keySet().forEach((keys) -> {
                expectedSystemPermission.addAll(systemPermissions.get(keys).stream().map(RoleSystemPermissionDTO::systemPermission).toList());
            });

            assertEquals(expectedSystemPermission.size(),actualSystemPermission.size());

            Map<String, Long> countOfPermissionsAvailableOnInstitution = new HashMap<>();

            this.institutePermission.keySet().forEach((keys) -> {
                List<InstitutionPermissionDTO> institutionPermissionDTOList = institutePermission.get(keys);
                for (InstitutionPermissionDTO institutionPermissionDTO : institutionPermissionDTOList) {
                    String institutionCode = institutionPermissionDTO.institutionCode();
                    if (countOfPermissionsAvailableOnInstitution.containsKey(institutionCode)) {
                        long count = countOfPermissionsAvailableOnInstitution.get(institutionCode);
                        countOfPermissionsAvailableOnInstitution.replace(institutionCode, count, count + 1);
                    } else {
                        countOfPermissionsAvailableOnInstitution.put(institutionCode, 1L);
                    }
                }
            });

            Map<String, Set<InstitutionPermission>> actualInstitutionPermission = user.getInstitutePermission();

            actualInstitutionPermission.keySet().forEach(keys -> {
                assertEquals(countOfPermissionsAvailableOnInstitution.get(keys), actualInstitutionPermission.get(keys).size());
            });
        }

    }

}