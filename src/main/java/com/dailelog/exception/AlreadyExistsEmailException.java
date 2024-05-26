package com.dailelog.exception;

public class AlreadyExistsEmailException extends DailelogException {

    private static final String MESSAGE = "이미 가입된 아이디입니다.";

    public AlreadyExistsEmailException() {
        super(MESSAGE);
    }

    @Override
    public int statusCode() {
        return 400;
    }
}
