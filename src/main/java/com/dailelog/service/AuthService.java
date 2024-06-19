package com.dailelog.service;

import com.dailelog.crypto.PasswordEncoder;
import com.dailelog.crypto.ScryptPasswordEncoder;
import com.dailelog.domain.User;
import com.dailelog.exception.AlreadyExistsAccountException;
import com.dailelog.exception.InvalidSinginInformation;
import com.dailelog.repository.UserRepository;
import com.dailelog.request.Login;
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
    public Long signin(Login login) {
        User user = userRepository.findByLoginId(login.getLoginId())
                .orElseThrow(InvalidSinginInformation::new); //사용자가 없는 것인지 비밀번호가 틀린것인지 취약점이 노출될 수 있어서

        var matches = passwordEncoder.matches(login.getPassword(), user.getPassword());
        if (!matches) {
            throw new InvalidSinginInformation();
        }
        //Session session = user.addSession();
        return user.getId();
    }
    @Transactional
    public void signup(Signup signup) {
        Optional<User> userOptional = userRepository.findByLoginId(signup.getLoginId());
        if (userOptional.isPresent()) {
            throw new AlreadyExistsAccountException();
        }

        String encryptedPassword = passwordEncoder.encrypt(signup.getPassword());

        userRepository.save(User.builder()
                .name(signup.getName())
                .password(encryptedPassword)
                .loginId(signup.getLoginId())
                .build());
    }
}
