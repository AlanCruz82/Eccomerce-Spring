package com.spring.eccomerce.exception;

public class StorageExcepcion extends RuntimeException {
    public StorageExcepcion(String message) {
        super(message);
    }

    public StorageExcepcion(String message, Throwable cause) {
        super(message);
    }
}
