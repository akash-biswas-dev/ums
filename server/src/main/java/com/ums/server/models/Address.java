package com.ums.server.models;


import com.ums.server.models.address.Country;
import com.ums.server.models.address.District;
import com.ums.server.models.address.State;
import jakarta.persistence.*;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "building_no")
    private String buildingNo;
    private String street;
    private String city;
    private District district;
    private State state;
    private Country country;
}
