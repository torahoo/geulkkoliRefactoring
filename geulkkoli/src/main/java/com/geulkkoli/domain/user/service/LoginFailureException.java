package com.geulkkoli.domain.user.service;

public class LoginFailureException extends RuntimeException{

    public LoginFailureException(String message) {
        super(message);
    }
}
