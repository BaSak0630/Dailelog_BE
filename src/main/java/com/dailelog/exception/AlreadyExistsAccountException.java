package com.dailelog.exception;

public class AlreadyExistsAccountException extends DailelogException {

    private static final String MESSAGE = "이미 가입된 아이디입니다.";

    public AlreadyExistsAccountException() {
        super(MESSAGE);
    }

    @Override
    public int statusCode() {
        return 400;
    }
}
