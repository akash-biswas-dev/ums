package com.ums.server.models;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "institution")
public class Institution {
    @Id
    @Column(length = 50)
    private String code;

    private String name;

    private LocalDate createdOn;

}
