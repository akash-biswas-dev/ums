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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "institution")
public class Institution {
    @Id
    @Column(length = 50)
    private String code;

    private String name;

    private LocalDate createdOn;

    @ManyToOne
    @JoinColumn(name = "principal", referencedColumnName = "user_id")
    private StuffProfile principal;
    @ManyToOne
    @JoinColumn(name = "director", referencedColumnName = "user_id")
    private StuffProfile director;
}
