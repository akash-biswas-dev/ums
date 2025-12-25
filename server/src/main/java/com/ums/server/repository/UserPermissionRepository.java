package com.ums.server.repository;

import com.ums.server.dtos.projections.PermissionProjection;
import com.ums.server.models.UserPermission;
import com.ums.server.models.UserPermissionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPermissionRepository  extends JpaRepository <UserPermission, UserPermissionId>{

    List<PermissionProjection> findById_StuffId(String userId);
}
