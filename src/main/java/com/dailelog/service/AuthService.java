package com.dailelog.service;

import com.dailelog.domain.User;
import com.dailelog.exception.AlreadyExistsAccountException;
import com.dailelog.repository.UserRepository;
import com.dailelog.request.Signup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public void signup(Signup signup) {
        Optional<User> userOptional = userRepository.findByAccount(signup.getAccount());
        if (userOptional.isPresent()) {
            throw new AlreadyExistsAccountException();
        }

        String encryptedPassword = passwordEncoder.encode(signup.getPassword());

        userRepository.save(User.builder()
                .name(signup.getName())
                .password(encryptedPassword)
                        .email(signup.getEmail())
                .account(signup.getAccount())
                .build());
    }
}
