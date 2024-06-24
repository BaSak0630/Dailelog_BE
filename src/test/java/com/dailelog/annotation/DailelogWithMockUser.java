package com.dailelog.annotation;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = DailelogMockUserFactory.class)
public @interface DailelogWithMockUser {

    String username() default "daile";

    String password() default "1234";

    String email() default "daile@daile.com";

    String name() default "daile";

    String role() default "ROLE_ADMIN";
}
