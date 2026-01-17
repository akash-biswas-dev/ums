package com.ums.server.repository;

import com.ums.server.dtos.db.RoleIdDTO;
import com.ums.server.dtos.db.RoleWithInstitutionCodeDTO;
import com.ums.server.models.UserRole;
import com.ums.server.models.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {

    @Query("""
            SELECT new com.ums.server.dtos.db.RoleIdDTO(
                ur.id.roleId
                ) FROM UserRole ur WHERE ur.id.userId=:userId
            """)
    List<RoleIdDTO> findAllRolesByUserId(String userId);
}
