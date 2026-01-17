package com.ums.server.models;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    private String id;
    @Column(length = 100)
    private String name;
    @Column(length = 500)
    private String description;

    @Column(name = "created_on", nullable = false)
    private LocalDate createdOn;

    @JoinColumn(name = "institution_code", referencedColumnName = "code")
    @ManyToOne(fetch = FetchType.LAZY)
    private Institution institution;
}
