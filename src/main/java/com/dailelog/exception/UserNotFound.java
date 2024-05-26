package com.dailelog.exception;

public class UserNotFound extends DailelogException{

    private static final String MESSAGE = "존재하지 않는 아이디입니다.";

    public UserNotFound() {
        super(MESSAGE);
    }


    @Override
    public int statusCode() {
        return 404;
    }
}
