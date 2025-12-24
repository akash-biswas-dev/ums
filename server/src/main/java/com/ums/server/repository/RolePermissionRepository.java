package com.ums.server.repository;

import com.ums.server.models.RolePermission;
import com.ums.server.models.RolePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionRepository extends JpaRepository<RolePermission, RolePermissionId> {
}
