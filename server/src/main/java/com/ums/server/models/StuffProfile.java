package com.ums.server.models;


import jakarta.persistence.*;

@Entity
@Table(name = "stuff_profiles")
public class StuffProfile {
    @Id
    private String id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UmsUsers user;

   // Define all the stuff related data like salary details and other things.
}
