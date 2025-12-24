package com.ums.server.config;


import com.ums.server.models.Role;
import com.ums.server.models.RolePermission;
import com.ums.server.models.UmsUsers;
import com.ums.server.repository.RolePermissionRepository;
import com.ums.server.repository.RoleRepository;
import com.ums.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
@Profile({"admin-config"})
public class AdminUserConfig {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final RoleRepository roleRepository;

    private static final List<String> ADMIN_PERMISSION = List.of(

    );

    @Bean
    CommandLineRunner commandLineRunner(Environment environment) {
        String adminRoleName = environment.getProperty("admin.role");
        String adminEmail = environment.getProperty("admin.email");
        return args -> {

            Optional<Role> adminRole = roleRepository.findById("Admin");
            if (adminRole.isPresent()) {
                return;
            }
            Role savedRole = roleRepository.save(new Role(
                    "Admin",
                    "This is system generated role, but dont change the description."
            ));

            List<RolePermission> rolePermissions = ADMIN_PERMISSION.stream().map((permission) -> new RolePermission(savedRole.getName(), permission)).toList();

            rolePermissionRepository.saveAll(rolePermissions);

            Optional<UmsUsers> adminUserOptional = userRepository.findByEmailIgnoreCase(adminEmail);

            if (adminUserOptional.isPresent()) {
                return;
            }

            UmsUsers adminUser = UmsUsers
                    .builder()
                    .email(adminEmail)
                    .password(passwordEncoder.encode("password"))
                    .isEnabled(false)
                    .isLocked(false)
                    .build();
            UmsUsers savedUser = userRepository.save(adminUser);
        };
    }
}
