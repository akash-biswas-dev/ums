package com.ums.server.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "role_permissions")
@AllArgsConstructor
@NoArgsConstructor
public class RolePermission {

    @EmbeddedId
    private RolePermissionId id;

    @MapsId(value = "roleName")
    @JoinColumn(name = "role_name", referencedColumnName = "name")
    @ManyToOne(fetch = FetchType.LAZY)
    private Role role;

    public RolePermission(String roleName, UmsPermissions permission) {
        this.id = new RolePermissionId(roleName, permission);
    }
}
