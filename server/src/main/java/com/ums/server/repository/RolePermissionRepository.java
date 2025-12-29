package com.ums.server.repository;

import com.ums.server.dtos.projections.InstitutionPermissionProjection;
import com.ums.server.dtos.projections.SystemPermissionProjection;
import com.ums.server.models.RolePermission;
import com.ums.server.models.RolePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RolePermissionRepository extends JpaRepository<RolePermission, RolePermissionId> {

    @Query(
            nativeQuery = true,
            value = "SELECT rp.permission, r.institution_code FROM " +
                    "role_permissions AS rp JOIN role AS r " +
                    "ON rp.role_id = r.id"
    )
    List<InstitutionPermissionProjection> findAllPermissionsRespectToInstitute(String roleId);
}
