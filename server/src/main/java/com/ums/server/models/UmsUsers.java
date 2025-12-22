package com.ums.server.models;


import lombok.*;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UmsUsers implements UserDetails {
    private String id;
    private String enrollmentId;
    private String email;
    private String password;
    private Gender gender;
    private LocalDate joinedDate;
    private Boolean isLocked;
    private Boolean isEnabled;


    private List<String> authorities;

    @Override
    @NullMarked
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities.stream().map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    public @Nullable String getPassword() {
        return this.password;
    }

    @NonNull
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
