package com.ums.server.models;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@Table(name = "role")
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    private String id;
    @Column(length = 100)
    private String name;
    @Column(length = 500)
    private String description;

    @JoinColumn(name = "institution_code", referencedColumnName = "code")
    @ManyToOne(fetch = FetchType.LAZY)
    private Institution institution;
}
