package com.ums.server.models;


import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class StuffAssignedOn implements Serializable {

    private String userId;
    private String institutionCode;
    @Enumerated(EnumType.STRING)
    private StuffType stuffType;
}
