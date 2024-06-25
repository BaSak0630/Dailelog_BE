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

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;


@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = DailelogMockSecurityContext.class)
public @interface DailelogMockUser {

    String account() default "daile";

    String password() default "";

    String email() default "daile@gmail.com";

    String name() default "김동혁";

    //String role() default "ROLE_ADMIN";
}