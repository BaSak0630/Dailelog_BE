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
        super(user.getAccount(), user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        //DB에서 사용자마다 권한이 다르면 적용해주어야함
        this.userId = user.getId();
    }

    public Long getUserId() {
        return userId;
    }
}
