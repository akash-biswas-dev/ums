package com.ums.server.repository;

import com.ums.server.dtos.db.InstitutionPermissionDTO;
import com.ums.server.models.RoleInstitutionPermission;
import com.ums.server.models.RoleInstitutionPermissionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleInstitutePermissionRepository extends JpaRepository<RoleInstitutionPermission, RoleInstitutionPermissionId> {

// FIXME: Write the join logic.
   @Query("""
           SELECT new com.ums.server.dtos.db.InstitutionPermissionDTO(rip.id.institutionPermission) FROM RoleInstitutionPermission rip JOIN rip.role r JOIN r.institution ins WHERE rip.id.roleId = :roleId AND 
           """)
    List<InstitutionPermissionDTO> findAllPermissionsByRoleId(String roleId);
}
