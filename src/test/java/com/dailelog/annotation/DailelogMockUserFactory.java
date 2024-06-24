package com.dailelog.annotation;

import com.dailelog.config.UserPrincipal;
import com.dailelog.domain.User;
import com.dailelog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;


@RequiredArgsConstructor
public class DailelogMockUserFactory implements WithSecurityContextFactory<DailelogWithMockUser> {
    private UserRepository userRepository;
    @Override
    public SecurityContext createSecurityContext(DailelogWithMockUser annotation) {
        String username = annotation.username();
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

