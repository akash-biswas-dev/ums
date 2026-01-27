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
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(length = 100)
    private String name;
    @Column(length = 500)
    private String description;

    @Column(name = "created_on", nullable = false)
    private LocalDate createdOn;

    public Role(String name , String description){
        this.name = name;
        this.description = description;
        this.createdOn = LocalDate.now();
    }
}
