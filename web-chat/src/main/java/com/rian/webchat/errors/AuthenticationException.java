package com.rian.webchat.errors;

public class AuthenticationException extends RuntimeException{

    public AuthenticationException(String message) {
        super(message);
    }
}
