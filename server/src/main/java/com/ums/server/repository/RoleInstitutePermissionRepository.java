package com.ums.server.repository;

import com.ums.server.dtos.db.InstitutionPermissionDTO;
import com.ums.server.models.RoleInstitutionPermission;
import com.ums.server.models.RoleInstitutionPermissionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleInstitutePermissionRepository extends JpaRepository<RoleInstitutionPermission, RoleInstitutionPermissionId> {

// FIXME: Write the join logic.
   @Query("""
           SELECT new com.ums.server.dtos.db.InstitutionPermissionDTO(
           r.institution.code,
           rip.id.institutionPermission)
           FROM RoleInstitutionPermission rip JOIN Role r ON
           r.id = rip.id.roleId WHERE rip.id.roleId = :roleId
           AND r.institution IS NOT NULL
           """)
    List<InstitutionPermissionDTO> findAllPermissionsByRoleId(@Param(value = "roleId") String roleId);
}
