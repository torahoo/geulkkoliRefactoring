package com.geulkkoli.domain.user;

public class NoSuchPostException extends RuntimeException {
    public NoSuchPostException(String message) {
        super(message);
    }
}
