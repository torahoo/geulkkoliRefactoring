package com.geulkkoli.application.user.util;

public interface UserRequestConverter<T, R> {
    R convert(T t);

    boolean supports(String registrationId);
}
