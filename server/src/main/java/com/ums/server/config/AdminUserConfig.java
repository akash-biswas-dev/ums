package com.ums.server.config;


import com.ums.server.models.Role;
import com.ums.server.models.RoleSystemPermission;
import com.ums.server.models.UmsUsers;
import com.ums.server.models.UserRole;
import com.ums.server.models.permission.SystemPermissions;
import com.ums.server.repository.RoleRepository;
import com.ums.server.repository.RoleSystemPermissionRepository;
import com.ums.server.repository.UmsUserRepository;
import com.ums.server.repository.UserRoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Transactional
@Configuration
@Slf4j
@RequiredArgsConstructor
public class AdminUserConfig {

    private final PasswordEncoder passwordEncoder;
    private final UmsUserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleSystemPermissionRepository roleSystemPermissionRepository;

    private static final List<SystemPermissions> SYSTEM_ADMIN_PERMISSIONS = List.of(
            SystemPermissions.SYSTEM_PERMISSION_MANAGE
    );

    @Bean
    CommandLineRunner commandLineRunner(Environment environment) {
        String adminEmail = environment.getProperty("ums.admin.email");

        if (adminEmail == null) {
            throw new IllegalArgumentException("Admin role or email cannot be null");
        }
        return args -> {

            Optional<Role> roleSystemAdmin = roleRepository.findByName("System Admin");

            if (roleSystemAdmin.isPresent()) {
                log.info("System admin already exist.");
            } else {
                Role newSystemAdminRole = new Role(
                        "System Admin",
                        "Users who having this roles can create and configure systems."
                );

                Role savedSystemAdminRole = roleRepository.save(newSystemAdminRole);
                log.info("New system admin role created.");

                List<RoleSystemPermission> roleSystemPermissions = new ArrayList<>();

                SYSTEM_ADMIN_PERMISSIONS.forEach(systemPermissions -> {
                    roleSystemPermissions.add(new RoleSystemPermission(
                            savedSystemAdminRole.getId(),
                            systemPermissions)
                    );
                });

                roleSystemPermissionRepository.saveAll(roleSystemPermissions);

                log.info("Assigned all system privileges to new system admin role.");

                UmsUsers newAdmin = new UmsUsers(
                        adminEmail,
                        passwordEncoder.encode("password"),
                        false,
                        false,
                        false
                );

                UmsUsers savedAdminUser = userRepository.save(newAdmin);
                log.info("New admin user created with email: {}.",adminEmail);

                userRoleRepository.save(new UserRole(
                        savedAdminUser.getId(),
                        savedSystemAdminRole.getId()
                ));
                log.info("Assign System Admin role to newly created admin.");
            }
        };
    }
}
