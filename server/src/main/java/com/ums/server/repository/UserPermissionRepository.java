package com.ums.server.repository;

import com.ums.server.dtos.projections.SystemPermissionProjection;
import com.ums.server.models.UserPermission;
import com.ums.server.models.UserPermissionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPermissionRepository  extends JpaRepository <UserPermission, UserPermissionId>{

    List<SystemPermissionProjection> findById_UserId(String userId);
}
