package com.ums.server.models;


import jakarta.persistence.*;

@Entity
@Table(name = "user_permissions")
public class UserPermission {

    @EmbeddedId
    private UserPermissionId userPermissionId;


    @MapsId("stuffId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stuff_id", referencedColumnName = "user_id")
    private StuffProfile stuffProfile;
}
