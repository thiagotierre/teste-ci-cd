package com.fiap.challenge.shared.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fiap.challenge.users.entity.UserModel;

public class UserPrincipal implements UserDetails {

	private static final long serialVersionUID = 1L;

	private final UserModel userModel;

    public UserPrincipal(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + userModel.getRole().name()));
    }

    @Override
    public String getPassword() { return userModel.getPasswordHash(); }

    @Override
    public String getUsername() { return userModel.getEmail(); }

    public UserModel getUserModel() {
        return userModel;
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}