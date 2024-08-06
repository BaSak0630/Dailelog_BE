package com.dailelog.service;

import com.dailelog.domain.User;
import com.dailelog.exception.AlreadyExistsAccountException;
import com.dailelog.repository.UserRepository;
import com.dailelog.request.Signup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

//@ActiveProfiles("test")
@SpringBootTest
class AuthServiceTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 성공")
    void test1() {
        // given
        Signup signup = Signup.builder()
                .account("userid")
                .password("1234")
                .name("김동혁")
                .email("user@email.com")
                .build();

        // when
        authService.signup(signup);

        // then
        assertEquals(1, userRepository.count());

        User user = userRepository.findAll().iterator().next();

        assertEquals("userid", user.getAccount());
        /*assertNotNull(user.getPassword());
        assertNotEquals("1234", user.getPassword()); //임시 평문이 아니다*/
        assertTrue(passwordEncoder.matches(signup.getPassword(), user.getPassword()));
        assertEquals("김동혁", user.getName());
    }

    @Test
    @DisplayName("회원가입시 중복된 아이디")
    void test2() {
        // given
        User user = User.builder()
                .account("daile123")
                .password("1234")
                .name("짱돌맨")
                .email("daile123@email.com")
                .build();
        userRepository.save(user); //사용자 비밀번호가 암호화 되어있지 않음

        Signup signup = Signup.builder()
                .account("daile123")
                .password("1234")
                .name("호돌맨")
                .email("daile123@email.com")
                .build();

        // expected
        assertThrows(AlreadyExistsAccountException.class, () -> authService.signup(signup));
    }
}