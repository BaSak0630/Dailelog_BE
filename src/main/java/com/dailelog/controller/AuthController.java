package com.dailelog.controller;

import com.dailelog.request.Login;
import com.dailelog.request.Signup;
import com.dailelog.response.SessionResponse;
import com.dailelog.service.AuthService;
import io.jsonwebtoken.Jwts;
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

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private static final String KEY= "s5ZweJAp9NjHKslNcN1cTlYJoTTl7dEpE3Cem4mF3aE=";

    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody @Valid Login login) {
        Long userId = authService.signin(login);

        /*
        SecretKey key = Jwts.SIG.HS256.key().build();
        byte[] encodeKey = key.getEncoded();  //키를 byte로 뽑아서
        String strKey = Base64.getEncoder().encodeToString(encodeKey); //Base64롤 다시 string encodeing 해주면 string 고정됨
        */
        SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(KEY));

        String jws = Jwts.builder()
                .subject(String.valueOf(userId))
                .signWith(key)
                .compact();

        return new SessionResponse(jws);
        /*ResponseCookie cookie = ResponseCookie.from("SESSION", accessToken)
                .domain("localhost") //todo 서버환경에따라 변경필요
                .path("/")
                .httpOnly(true)
                .maxAge(Duration.ofDays(30))
                .sameSite("Strict")
                .build();
        log.info(">>> cookie = {}",cookie);
        log.info(">>> cookie name = {}",cookie.getName());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();*/
    }

    @PostMapping("/auth/signup")
    public void signup(@RequestBody @Valid Signup signup){
        authService.signup(signup);
    }

}
