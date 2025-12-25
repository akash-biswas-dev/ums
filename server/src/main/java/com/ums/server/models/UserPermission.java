package com.ums.server.models;


import jakarta.persistence.*;

@Entity
@Table(name = "user_permissions")
public class UserPermission {

    @EmbeddedId
    private UserPermissionId userPermissionId;


    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UmsUsers users;
}
