package com.ums.server.repository;

import com.ums.server.models.UserPermission;
import com.ums.server.models.UserPermissionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPermissionRepository  extends JpaRepository <UserPermission, UserPermissionId>{
}
