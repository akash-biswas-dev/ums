package com.ums.server.models;


import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;


@Getter
@Setter
@Builder
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class UmsUsers implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

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
    private Set<UmsPermissions> authorities;

    @Override
    @NullMarked
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities.stream()
                .map(umsPermissions -> new SimpleGrantedAuthority(umsPermissions.name()))
                .toList();
    }

    @Override
    public @Nullable String getPassword() {
        return this.password;
    }

    @NullMarked
    @Override
    public String getUsername() {
        return this.id;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.isLocked;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}
