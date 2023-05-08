package com.geulkkoli.application.user;

public interface UserRequestConverter<T, R> {
    R convert(T t);
}
