package com.spring.eccomerce.exception.base;

public abstract class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}
