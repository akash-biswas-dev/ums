package com.ums.server.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role_institution_permissions")
public class RoleInstitutionPermission {

    @EmbeddedId
    private RoleInstitutionPermissionId id;

    @MapsId("roleId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

}
