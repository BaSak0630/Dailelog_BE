package com.dailelog.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
public class Signup {
    @NotBlank(message = "이름을 입력해주세요")
    private String name;

    @NotBlank(message = "아이디 입력해주세요")
    private String account; // 아이디

    @NotBlank(message = "비밀번호를 입력해주세요")
    private final String password;

    @NotBlank(message = "이메일을 입력해주세요")
    private final String email;

    @Builder
    public Signup(String name, String account, String password, String email) {
        this.name = name;
        this.account = account;
        this.password = password;
        this.email = email;
    }
}
