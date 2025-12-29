package com.ums.server.config;


import com.ums.server.models.*;
import com.ums.server.models.permission.SystemPermissions;
import com.ums.server.repository.RolePermissionRepository;
import com.ums.server.repository.RoleRepository;
import com.ums.server.repository.UserRepository;
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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Configuration
@Slf4j
@RequiredArgsConstructor
@Profile({"admin-config"})
public class AdminUserConfig {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final RoleRepository roleRepository;

    private static final List<SystemPermissions> ADMIN_PERMISSION = Arrays.stream(SystemPermissions.values()).toList();

    @Bean
    CommandLineRunner commandLineRunner(Environment environment, UserRoleRepository userRoleRepository) {
        String adminRoleName = environment.getProperty("ums.admin.role");
        String adminEmail = environment.getProperty("ums.admin.email");

        if (adminRoleName == null || adminEmail == null) {
            throw new IllegalArgumentException("Admin role or email cannot be null");
        }
        return args -> {
            final UmsUsers adminUser;

            Optional<UmsUsers> adminUserOptional = userRepository.findByEmailIgnoreCase(adminEmail);

            if (adminUserOptional.isPresent()) {
                adminUser = adminUserOptional.get();
                log.info("Admin user exists with id {}", adminUser.getId());
            } else {
                String password = UUID.randomUUID().toString();

                UmsUsers newAdmin = UmsUsers.builder()
                        .email(adminEmail)
                        .password(passwordEncoder.encode(password))
                        .isEnabled(false)
                        .isLocked(false)
                        .joinedOn(LocalDate.now())
                        .build();

                adminUser = userRepository.save(newAdmin);
                log.info("New Admin user created with password {}.", password);
            }

        };
    }
}
