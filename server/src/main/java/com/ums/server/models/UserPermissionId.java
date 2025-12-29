package com.ums.server.models;


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
public class UserPermissionId implements Serializable {

    private String userId;
    @Enumerated(EnumType.STRING)
    private SystemPermissions permission;
}
