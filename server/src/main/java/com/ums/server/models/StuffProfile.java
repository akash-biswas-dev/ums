package com.ums.server.models;


import jakarta.persistence.*;

@Entity
public class StuffProfile {

    @EmbeddedId
    private StuffProfileId id;

    @ManyToOne
    @MapsId("institutionCode")
    @JoinColumn(name = "institution_code", referencedColumnName = "code")
    private Institution institution;


    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private UmsUsers users;

}
