package com.documentflow.exceptions;


public class UserNotActiveException extends RuntimeException {
    public UserNotActiveException() {
        super();
    }

    public UserNotActiveException(String message) {
        super(message);
    }
}
