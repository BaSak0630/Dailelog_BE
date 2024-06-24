package com.dailelog.annotation;

import com.dailelog.domain.User;
import com.dailelog.repository.UserRepository;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

/*
public class MockUserFactory implements WithSecurityContextFactory<CustomWithMockUser> {
    private UserRepository userRepository;
    @Override
    public SecurityContext createSecurityContext(CustomWithMockUser annotation) {
        String username = annotation.username();
        String password = annotation.password();
        int level = annotation.level();
        userRepository.save(

        );
    }
}
*/
