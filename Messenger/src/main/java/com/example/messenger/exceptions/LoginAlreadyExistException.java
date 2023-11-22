package com.example.messenger.exceptions;

import lombok.Getter;

@Getter
public class LoginAlreadyExistException extends RuntimeException {
    private final String login;
    public LoginAlreadyExistException(String message, String login) {
        super(message);
        this.login = login;
    }
}
