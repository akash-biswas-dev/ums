package com.ums.server.models;


import com.ums.server.models.permission.InstitutionPermission;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class RoleInstitutionPermissionId implements Serializable {

    private String roleId;
    @Column(name = "institution_permission")
    private InstitutionPermission institutionPermission;
}
