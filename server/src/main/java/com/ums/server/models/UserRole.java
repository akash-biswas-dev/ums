package com.ums.server.models;


import com.ums.server.repository.UserRoleRepository;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "user_roles")
public class UserRole {

    @EmbeddedId
    private UserRoleId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UmsUsers users;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;


    public UserRole(String userId, String roleId){
        this.id = new UserRoleId(userId,roleId);
        this.users = UmsUsers.builder()
                .id(userId)
                .build();
        this.role = Role.builder()
                .id(roleId)
                .build();
    }
}
