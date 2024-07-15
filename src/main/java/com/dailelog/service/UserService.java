package com.dailelog.service;

import com.dailelog.domain.User;
import com.dailelog.exception.UserNotFound;
import com.dailelog.repository.UserRepository;
import com.dailelog.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponse getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);

        return new UserResponse(user);
    }
}
