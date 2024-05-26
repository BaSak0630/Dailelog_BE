package com.dailelog.service;

import com.dailelog.domain.Session;
import com.dailelog.domain.User;
import com.dailelog.exception.AlreadyExistsEmailException;
import com.dailelog.exception.InvalidRequest;
import com.dailelog.exception.InvalidSinginInformation;
import com.dailelog.exception.UserNotFound;
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

    @Transactional
    public String signin(Login login) {

        userRepository.findByLoginId(login.getLoginId())
                .orElseThrow(UserNotFound::new);

        User user = userRepository.findByLoginIdAndPassword(login.getLoginId(), login.getPassword())
                .orElseThrow(InvalidSinginInformation::new);

        Session session = user.addSession();

        return session.getAccessToken();
    }
    @Transactional
    public void signup(Signup signup) {
        Optional<User> userOptional = userRepository.findByLoginId(signup.getLoginId());
        if (userOptional.isPresent()) {
            throw new AlreadyExistsEmailException();
        }
        userRepository.save(User.builder()
                .name(signup.getName())
                .password(signup.getPassword())
                .loginId(signup.getLoginId())
                .build());
    }
}
