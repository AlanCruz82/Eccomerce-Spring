package com.spring.eccomerce.exception;

public class ImagenExcedeTamanoException extends RuntimeException {

    public ImagenExcedeTamanoException(int maxFileSizeMb) {
        super("La imagen supera el tamaño máximo permitido de " + maxFileSizeMb + " MB");
    }
}