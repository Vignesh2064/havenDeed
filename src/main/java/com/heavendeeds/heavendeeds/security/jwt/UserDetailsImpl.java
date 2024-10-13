package com.heavendeeds.heavendeeds.security.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.heavendeeds.heavendeeds.entities.HAVEENuserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    public UserDetailsImpl(Integer userId, String email, String password, GrantedAuthority authorities) {
        this.id = userId;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    private Integer id;
    private String username;
    private String email;
    @JsonIgnore
    private String password;
    private GrantedAuthority authorities;

    public static UserDetailsImpl build(HAVEENuserDetails user, String role) {
        GrantedAuthority authorities = new SimpleGrantedAuthority(role);
        return new UserDetailsImpl(user.getUserId(), user.getEmail(), user.getHaveeNuserAuthenticationDetails().getOtp(), authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> listRole = new ArrayList<GrantedAuthority>();
        listRole.add(authorities); // this is the problematic line!
        return listRole;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);

    }
}