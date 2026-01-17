package com.ums.server.repository;

import com.ums.server.dtos.db.RoleSystemPermissionDTO;
import com.ums.server.models.RoleSystemPermission;
import com.ums.server.models.RoleSystemPermissionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleSystemPermissionRepository extends JpaRepository<RoleSystemPermission, RoleSystemPermissionId> {

    @Query(value = """
SELECT new com.ums.server.dtos.db.RoleSystemPermissionDTO(rsp.id.permission) FROM RoleSystemPermission rsp WHERE rsp.id.roleId = :roleId
""")
    List<RoleSystemPermissionDTO> findAllPermissionsByRoleId(String roleId);
}
