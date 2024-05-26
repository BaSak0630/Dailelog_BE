package com.dailelog.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Signup {

    @NotBlank(message = "이름을 입력해주세요")
    private String name;

    @NotBlank(message = "아이디 입력해주세요")
    private String loginId; // 아이디

    @NotBlank(message = "비밀번호를 입력해주세요")
    public final String password;

    @Builder
    public Signup(String name,String password, String loginId) {
        this.password = password;
        this.loginId = loginId;
        this.name = name;
    }
}
