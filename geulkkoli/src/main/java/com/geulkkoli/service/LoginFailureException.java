package com.geulkkoli.service;

public class LoginFailureException extends RuntimeException{

    public LoginFailureException(String message) {
        super(message);
    }
}
