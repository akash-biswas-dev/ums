package com.ums.server.models;


import com.ums.server.models.address.Country;
import com.ums.server.models.address.District;
import com.ums.server.models.address.State;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "address")
@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "building_no")
    private String buildingNo;
    private String street;
    private String city;
    @Enumerated(EnumType.STRING)
    private District district;
    @Enumerated(EnumType.STRING)
    private State state;
    @Enumerated(EnumType.STRING)
    private Country country;
}
