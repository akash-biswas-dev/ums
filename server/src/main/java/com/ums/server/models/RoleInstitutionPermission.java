package com.ums.server.models;


import com.ums.server.models.permission.InstitutionPermission;
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

    @MapsId("institutionCode")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institution_code", referencedColumnName = "code")
    private Institution institution;

    @MapsId("roleId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    private RoleInstitutionPermission(
            String roleId,
            String institutionCode,
            InstitutionPermission permission
    ){
        this.role = Role.builder().id(roleId).build();
        this.institution = Institution.builder().code(institutionCode).build();
        this.id = new RoleInstitutionPermissionId(roleId,institutionCode,permission);

    }

}
