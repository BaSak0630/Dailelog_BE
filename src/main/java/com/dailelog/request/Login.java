package com.dailelog.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Login {
    @NotBlank(message = "이메일을 입력해주세요") //값이 주입될때 검증을 해줌
    private final String loginId;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private final String password;

    @Builder
    public Login(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }
}
