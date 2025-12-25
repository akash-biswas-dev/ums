package com.ums.server.models;


import jakarta.persistence.*;

@Entity
@Table(name = "stuff_profile")
public class StuffProfile {
    @Id
    private String id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UmsUsers user;
}
