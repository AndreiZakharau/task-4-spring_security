package com.epam.esm.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthDateException extends AuthenticationException {
    public AuthDateException(String message) {
        super(message);
    }
}
