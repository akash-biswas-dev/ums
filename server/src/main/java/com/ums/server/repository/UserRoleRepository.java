package com.ums.server.repository;

import com.ums.server.dtos.projections.RoleNameProjection;
import com.ums.server.models.UserRole;
import com.ums.server.models.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {

    List<RoleNameProjection> findById_UserId(String id);
}
