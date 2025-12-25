package com.ums.server.models;


import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@Table(name = "user_permissions")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPermission {

    @EmbeddedId
    private UserPermissionId id;


    @MapsId("stuffId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stuff_id", referencedColumnName = "user_id")
    private StuffProfile stuffProfile;
}
