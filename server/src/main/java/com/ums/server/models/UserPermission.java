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


    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UmsUsers users;
}
