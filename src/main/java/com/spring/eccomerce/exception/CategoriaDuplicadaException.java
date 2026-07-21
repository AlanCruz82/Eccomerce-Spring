package com.spring.eccomerce.exception;

import com.spring.eccomerce.exception.base.DuplicateResourceException;

public class CategoriaDuplicadaException extends DuplicateResourceException {
    public CategoriaDuplicadaException(String nombre) {
        super("La categoria con el nombre " + nombre + " ya esta registrada");
    }
}
