package com.spring.eccomerce.exception;

import com.spring.eccomerce.exception.base.ResourceNotFoundException;

public class CategoriaNotFoundException extends ResourceNotFoundException {
    public CategoriaNotFoundException(Long id) {
        super("La categoria con el id " + id + " no existe");
    }

    public CategoriaNotFoundException(String message) {
        super(message);
    }
}
