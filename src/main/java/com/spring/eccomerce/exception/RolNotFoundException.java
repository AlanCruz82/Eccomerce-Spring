package com.spring.eccomerce.exception;

import com.spring.eccomerce.exception.base.ResourceNotFoundException;

public class RolNotFoundException extends ResourceNotFoundException {
    public RolNotFoundException(String nombre) {
        super("El rol " + nombre + " no existe");
    }
}
