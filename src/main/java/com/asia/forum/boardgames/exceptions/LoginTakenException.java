package com.asia.forum.boardgames.exceptions;

public class LoginTakenException extends RuntimeException {
    public LoginTakenException(String message) {
        super(message);
    }
}
