package com.ums.server.models;


import com.ums.server.models.address.CountryCode;
import com.ums.server.models.permission.InstitutionPermission;
import com.ums.server.models.permission.SystemPermissions;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

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
    @JoinColumn(name = "permanent_address", referencedColumnName = "id")
    private Address permanentAddress;


    @Column(nullable = false, name = "joined_on")
    private LocalDate joinedOn;

    @Column(nullable = false, name = "is_locked")
    private Boolean isLocked;

    @Column(nullable = false, name = "is_enabled")
    private Boolean isEnabled;

    @Column(name = "is_profile_completed")
    private Boolean isProfileUpdated;

    @Transient
    @Setter(AccessLevel.NONE)
    Set<SystemPermissions> systemPermissions;

    @Transient
    @Setter(AccessLevel.NONE)
    Map<String, Set<InstitutionPermission>> institutePermission;

    public void setPermissions(
            Set<SystemPermissions> permissions,
            Map<String, Set<InstitutionPermission>> institutePermission
    ) {
        this.systemPermissions = permissions;
        this.institutePermission = institutePermission;
    }

}
