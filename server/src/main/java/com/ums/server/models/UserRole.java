package com.ums.server.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles")
public class UserRole{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String roleName;
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "institution_code", referencedColumnName = "code")
    private Institution institution;

}
