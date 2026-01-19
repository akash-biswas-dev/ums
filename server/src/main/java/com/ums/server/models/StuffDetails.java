package com.ums.server.models;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "stuff_details",
        uniqueConstraints = @UniqueConstraint(
                name = "unique_stuff_details",
                columnNames = {
                        "stuff_id",
                        "institution_code",
                        "stuff_type"
                }
        ))
public class StuffDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stuff_id", referencedColumnName = "user_id")
    private StuffProfile stuffProfile;

    @JoinColumn(name = "institution_code", referencedColumnName = "code")
    @ManyToOne(fetch = FetchType.LAZY)
    private Institution institution;

    @Column(name = "stuff_type")
    @Enumerated(EnumType.STRING)
    private StuffType stuffType;


    @Column(name = "starting_from", nullable = false)
    private LocalDate startingFrom;

    @Column(name = "ending_at")
    private LocalDate endingAt;

}
