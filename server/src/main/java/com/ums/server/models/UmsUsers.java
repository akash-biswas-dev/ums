package com.ums.server.models;


import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.NullMarked;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@Builder
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class UmsUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "phone_country")
    private CountryCode phoneCountry;

    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "alternate_phone_country")
    private CountryCode alternatePhoneCountry;

    @Column(name = "alternate_phone")
    private String alternatePhone;

    @OneToOne
    @JoinColumn(name = "current_address", referencedColumnName = "id")
    private Address currentAddress;

    @OneToOne
    @JoinColumn(name = "permanent_address",referencedColumnName = "id")
    private Address permanentAddress;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false, name = "joined_on")
    private LocalDate joinedOn;

    @Column(nullable = false, name = "is_locked")
    private Boolean isLocked;

    @Column(nullable = false, name = "is_enabled")
    private Boolean isEnabled;

    @Transient
    @Getter(AccessLevel.NONE)
    private Set<UmsPermissions> permissions;

    @NullMarked
    public List<UmsPermissions> getPermissions() {
        return this.permissions.stream().toList();
    }

}
