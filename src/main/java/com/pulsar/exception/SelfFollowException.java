package com.pulsar.exception;

public class SelfFollowException extends RuntimeException {
    
    public SelfFollowException(String message) {
        super(message);
    }
    
    public SelfFollowException(String message, Throwable cause) {
        super(message, cause);
    }
}