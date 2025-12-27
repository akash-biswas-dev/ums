package com.ums.server.models;


import jakarta.persistence.*;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
}
