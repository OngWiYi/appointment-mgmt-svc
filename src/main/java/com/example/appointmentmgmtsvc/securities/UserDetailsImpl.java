/*
 * Copyright (c) Wi Yi 2023.
 */

package com.example.appointmentmgmtsvc.securities;

import com.example.appointmentmgmtsvc.entities.ApiUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Getter
public class UserDetailsImpl implements UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;
    private long id;
    private String username;
    private String email;
    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl build(ApiUser apiUser) {
        List<SimpleGrantedAuthority> authorities = apiUser.getRoles().stream()
                                                          .map(role -> new SimpleGrantedAuthority(role.getName()
                                                                                                      .name()))
                                                          .toList();
        return new UserDetailsImpl(apiUser.getId(),
                                   apiUser.getUsername(),
                                   apiUser.getEmail(),
                                   apiUser.getPassword(),
                                   authorities);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
