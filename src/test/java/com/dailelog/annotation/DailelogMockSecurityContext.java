package com.dailelog.annotation;

import com.dailelog.config.UserPrincipal;
import com.dailelog.domain.User;
import com.dailelog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;


@RequiredArgsConstructor
public class DailelogMockSecurityContext implements WithSecurityContextFactory<DailelogMockUser> {
    private final UserRepository userRepository;
    @Override
    public SecurityContext createSecurityContext(DailelogMockUser annotation) {
        String username = annotation.account();
        String password = annotation.password();
        String name = annotation.name();
        String email = annotation.email();
        var user = User.builder()
                .account(username)
                .password(password)
                .email(email)
                .name(name)
                .build();
        userRepository.save(user);

        var principal = new UserPrincipal(user);

        var role = new SimpleGrantedAuthority("ROLE_ADMIN");

        var authenticationToken = new UsernamePasswordAuthenticationToken(principal, user.getPassword(), List.of(role));

        var context = SecurityContextHolder.createEmptyContext();

        context.setAuthentication(authenticationToken);

        return context;
    }
}