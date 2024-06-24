package com.dailelog.annotation;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
//@WithSecurityContext(factory = MockUSerFactory.class)
public @interface CustomWithMockUser {

    String username() default "daile";

    String password() default "1234";

    int level() default 5;

    String mobileNumber() default "123456789";
}
