package com.ums.server.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "user_role")
@AllArgsConstructor
@NoArgsConstructor
public class UserRole {

    @EmbeddedId
    private UserRoleId id;

    @ManyToOne
    @MapsId("roleName")
    @JoinColumn(name = "role_name", referencedColumnName = "name")
    private Role role;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private StuffProfile stuff;

    @Column(name = "starting_from", nullable = false)
    private LocalDate startingFrom;

    public UserRole(String roleName, String userId, LocalDate startingFrom) {
        this.id = new UserRoleId(userId, roleName);
        this.startingFrom = startingFrom;
    }

    public String getRoleName(){
        return this.id.getRoleName();
    }
}
