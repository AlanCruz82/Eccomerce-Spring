package com.spring.eccomerce.exception;

import com.spring.eccomerce.exception.base.DuplicateResourceException;

public class ProductoDuplicadoException extends DuplicateResourceException {
    public ProductoDuplicadoException(String nombre) {
        super("El producto con el nombre " + nombre + " ya existe");
    }
}
