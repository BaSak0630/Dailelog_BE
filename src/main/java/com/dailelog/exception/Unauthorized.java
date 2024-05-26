package com.dailelog.exception;

public class Unauthorized extends DailelogException{

    private static final String MESSAGE = "인증 필요합니다.";

    public Unauthorized() {
        super(MESSAGE);
    }

    @Override
    public int statusCode() {
        return 401;
    }
}
