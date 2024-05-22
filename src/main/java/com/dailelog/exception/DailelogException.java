package com.dailelog.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class DailelogException extends RuntimeException {

    public final Map<String, String> validation = new HashMap<>();

    public DailelogException(String message) {
        super(message);
    }

    public DailelogException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int statusCode();

    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }
}
