package com.ums.server.models;


import com.ums.server.models.permission.InstitutionPermission;
import com.ums.server.models.permission.SystemPermissions;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "role_permissions")
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RolePermission {

    @EmbeddedId
    private RolePermissionId id;

    @MapsId(value = "roleId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;


    public RolePermission(String roleName, InstitutionPermission permission) {
        this.id = new RolePermissionId(roleName, permission);
    }
}
