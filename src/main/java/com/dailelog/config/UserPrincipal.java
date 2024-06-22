package com.dailelog.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserPrincipal extends User {

    private final Long userId;

    public UserPrincipal(com.dailelog.domain.User user) {
        super(user.getAccount(), user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.userId = user.getId();
    }

    public Long getUserId() {
        return userId;
    }
}
