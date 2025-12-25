package com.ums.server.models;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "stuff_details")
public class StuffProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Embedded
    private StuffAssignedOn assignedOn;

    @Column(name = "starting_from", nullable = false)
    private LocalDate startingFrom;

    @Column(name = "ending_at")
    private LocalDate endingAt;

    @ManyToOne
    @MapsId("institutionCode")
    @JoinColumn(name = "institution_code", referencedColumnName = "code")
    private Institution institution;


    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UmsUsers users;


}
