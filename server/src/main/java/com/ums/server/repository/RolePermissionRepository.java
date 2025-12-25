package com.ums.server.repository;

import com.ums.server.dtos.projections.PermissionProjection;
import com.ums.server.models.RolePermission;
import com.ums.server.models.RolePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolePermissionRepository extends JpaRepository<RolePermission, RolePermissionId> {

    List<PermissionProjection> findById_RoleName(String roleName);
}
