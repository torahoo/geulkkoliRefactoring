package com.geulkkoli.domain.user;

public class NoSuchFollowException extends RuntimeException {
    public NoSuchFollowException(String message) {
        super(message);
    }
}
