package com.ums.server.models;


import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_permissions")
public class UserPermission {

    @EmbeddedId
    private UserPermissionId userPermissionId;


    private UmsUsers users;
}
