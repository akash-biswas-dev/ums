package com.ums.server.dtos.db;

import com.ums.server.models.permission.SystemPermissions;

public record RoleSystemPermissionDTO(
    SystemPermissions systemPermission
){
}
