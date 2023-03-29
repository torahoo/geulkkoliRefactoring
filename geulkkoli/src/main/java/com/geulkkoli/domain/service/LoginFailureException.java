package com.geulkkoli.domain.service;

public class LoginFailureException extends RuntimeException{

    public LoginFailureException(String message) {
        super(message);
    }
}
