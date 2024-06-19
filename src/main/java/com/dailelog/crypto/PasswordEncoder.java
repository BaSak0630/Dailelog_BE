package com.dailelog.crypto;

import org.springframework.stereotype.Component;

@Component
public interface PasswordEncoder {

    String encrypt(String rawPassword);

    boolean matches(String rawPassword, String encodedPassword);
}
