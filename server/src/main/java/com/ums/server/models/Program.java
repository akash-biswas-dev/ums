package com.ums.server.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "programs")
public class Program {

    @Id
    @Column(length = 50)
    private String code;
    @Column(length = 200, nullable = false, unique = true)
    private String name;
}
