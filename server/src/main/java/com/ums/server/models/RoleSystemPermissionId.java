package com.ums.server.models;


import com.ums.server.models.permission.InstitutionPermission;
import com.ums.server.models.permission.SystemPermissions;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class RoleSystemPermissionId implements Serializable {

    @Column(length = 36)
    private String roleId;
    @Enumerated(EnumType.STRING)
    @Column(name = "system_permission")
    private SystemPermissions permission;
}
