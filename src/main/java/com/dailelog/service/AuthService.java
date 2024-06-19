package com.dailelog.service;

import com.dailelog.crypto.ScryptPasswordEncoder;
import com.dailelog.domain.User;
import com.dailelog.exception.AlreadyExistsAccountException;
import com.dailelog.repository.UserRepository;
import com.dailelog.request.Signup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ScryptPasswordEncoder passwordEncoder;


    @Transactional
    public void signup(Signup signup) {
        Optional<User> userOptional = userRepository.findByLoginId(signup.getAccount());
        if (userOptional.isPresent()) {
            throw new AlreadyExistsAccountException();
        }

        String encryptedPassword = passwordEncoder.encrypt(signup.getPassword());

        userRepository.save(User.builder()
                .name(signup.getName())
                .password(encryptedPassword)
                        .email(signup.getEmail())
                .account(signup.getAccount())
                .build());
    }
}
