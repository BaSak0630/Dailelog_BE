package com.dailelog.controller;

import com.dailelog.config.AppConfig;
import com.dailelog.request.Login;
import com.dailelog.request.Signup;
import com.dailelog.response.SessionResponse;
import com.dailelog.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AppConfig appConfig;

    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody @Valid Login login) {
        Long userId = authService.signin(login);


        SecretKey key = Keys.hmacShaKeyFor(appConfig.getJwtKey());

        String jws = Jwts.builder()
                .subject(String.valueOf(userId))
                .signWith(key)
                .issuedAt(new Date())//생성 시간
                //.expiration() //todo 만료여부체킹
                .compact();

        return new SessionResponse(jws);
    }

    @PostMapping("/auth/signup")
    public void signup(@RequestBody @Valid Signup signup){
        authService.signup(signup);
    }

}
