package com.ums.server.models;


import com.ums.server.models.permission.InstitutionPermission;
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
@NoArgsConstructor
@AllArgsConstructor
public class RoleInstitutionPermissionId implements Serializable {

    @Column(length = 36)
    private String roleId;
    private String institutionCode;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "institution_permission")
    private InstitutionPermission institutionPermission;
}
