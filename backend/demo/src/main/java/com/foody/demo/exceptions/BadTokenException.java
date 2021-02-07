package com.foody.demo.exceptions;

public class BadTokenException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Token is invalid or expired";
    }
}
