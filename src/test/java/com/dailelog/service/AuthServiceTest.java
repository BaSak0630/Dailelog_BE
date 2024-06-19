package com.dailelog.service;

import com.dailelog.crypto.PasswordEncoder;
import com.dailelog.crypto.ScryptPasswordEncoder;
import com.dailelog.domain.User;
import com.dailelog.exception.AlreadyExistsAccountException;
import com.dailelog.exception.InvalidSinginInformation;
import com.dailelog.repository.UserRepository;
import com.dailelog.request.Login;
import com.dailelog.request.Signup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

//@ActiveProfiles("test")
@SpringBootTest
class AuthServiceTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @AfterEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 성공")
    void test1() {
        // given
        PasswordEncoder encoder = new ScryptPasswordEncoder();
        Signup signup = Signup.builder()
                .loginId("userid")
                .password("1234")
                .name("김동혁")
                .build();

        // when
        authService.signup(signup);

        // then
        assertEquals(1, userRepository.count());

        User user = userRepository.findAll().iterator().next();

        assertEquals("userid", user.getLoginId());
        /*assertNotNull(user.getPassword());
        assertNotEquals("1234", user.getPassword()); //임시 평문이 아니다*/
        assertTrue(encoder.matches(signup.getPassword(), user.getPassword()));
        assertEquals("김동혁", user.getName());
    }

    @Test
    @DisplayName("회원가입시 중복된 아이디")
    void test2() {
        // given
        User user = User.builder()
                .loginId("daile123")
                .password("1234")
                .name("짱돌맨")
                .build();
        userRepository.save(user); //사용자 비밀번호가 암호화 되어있지 않음

        Signup signup = Signup.builder()
                .loginId("daile123")
                .password("1234")
                .name("호돌맨")
                .build();

        // expected
        assertThrows(AlreadyExistsAccountException.class, () -> authService.signup(signup));
    }

    @Test
    @DisplayName("로그인 성공")
    void test3() {
        // given
        ScryptPasswordEncoder encoder = new ScryptPasswordEncoder();
        String ecnryptedPassword = encoder.encrypt("1234");

        User user = User.builder()
                .loginId("daile123")
                .password(ecnryptedPassword)
                .name("김동혁")
                .build();
        userRepository.save(user);  //db에 암호화 저장

        Login login = Login.builder()
                .loginId("daile123")
                .password("1234")
                .build();

        // when
        Long userId = authService.signin(login);

        // then
        assertNotNull(userId);
    }

    @Test
    @DisplayName("비밀번호 틀림")
    void test4() {
        // given
        ScryptPasswordEncoder encoder = new ScryptPasswordEncoder();
        String ecnryptedPassword = encoder.encrypt("1234");

        User user = User.builder()
                .loginId("daile123")
                .password(ecnryptedPassword)
                .name("김동혁")
                .build();
        userRepository.save(user);  //db에 암호화 저장


        Login login = Login.builder()
                .loginId("daile123")
                .password("5678")
                .build();

        // expected
        assertThrows(InvalidSinginInformation.class,()->authService.signin(login));

    }
}