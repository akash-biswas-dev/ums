package com.ums.server.dtos.db;

import com.ums.server.models.permission.InstitutionPermission;

public record InstitutionPermissionDTO(
        String institutionCode,
        InstitutionPermission institutionPermission
) {
}
