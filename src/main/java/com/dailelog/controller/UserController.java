package com.dailelog.controller;

import com.dailelog.config.UserPrincipal;
import com.dailelog.repository.UserRepository;
import com.dailelog.response.UserResponse;
import com.dailelog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userServcie;

    @GetMapping("/users/me")
    public ResponseEntity<UserResponse>  getMe(@AuthenticationPrincipal UserPrincipal userPrincipal){
        if(userPrincipal == null){
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserResponse userResponse = userServcie.getUserProfile(userPrincipal.getUserId());

        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }
}
