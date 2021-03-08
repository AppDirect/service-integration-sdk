package com.appdirect.sdk.web.oauth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.appdirect.sdk.appmarket.Credentials;


public class BasicAuthUserDetailsService implements UserDetails {

    private Credentials credentials;

    public BasicAuthUserDetailsService(Credentials credentials) {
        this.credentials = credentials;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return new BCryptPasswordEncoder().encode(credentials.getDeveloperSecret());
    }

    @Override
    public String getUsername() {
        return credentials.getDeveloperKey();
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
