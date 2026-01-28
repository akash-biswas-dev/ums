package com.ums.server.repository;

import com.ums.server.dtos.db.InstitutionPermissionDTO;
import com.ums.server.models.RoleInstitutionPermission;
import com.ums.server.models.RoleInstitutionPermissionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleInstitutionPermissionRepository extends JpaRepository<RoleInstitutionPermission, RoleInstitutionPermissionId> {

// FIXME: Write the join logic.
   @Query("""
           SELECT new com.ums.server.dtos.db.InstitutionPermissionDTO(
           rip.id.institutionCode,
           rip.id.institutionPermission)
           FROM RoleInstitutionPermission rip
           WHERE rip.id.roleId = :roleId
           """)
    List<InstitutionPermissionDTO> findAllPermissionsByRoleId(@Param(value = "roleId") String roleId);
}
