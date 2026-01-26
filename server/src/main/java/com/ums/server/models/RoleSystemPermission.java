package com.ums.server.models;


import com.ums.server.models.permission.InstitutionPermission;
import com.ums.server.models.permission.SystemPermissions;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "role_system_permissions")
public class RoleSystemPermission {

    @EmbeddedId
    private RoleSystemPermissionId id;

    @MapsId(value = "roleId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    public RoleSystemPermission(String roleId, SystemPermissions permission) {
        this.role = Role.builder()
                .id(roleId)
                .build();
        this.id= new RoleSystemPermissionId(roleId,permission);
    }
}
