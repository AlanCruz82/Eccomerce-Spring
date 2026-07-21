package com.spring.eccomerce.exception;

import com.spring.eccomerce.exception.base.ResourceNotFoundException;

public class ProductoNotFoundException extends ResourceNotFoundException {
    public ProductoNotFoundException(Long id) {
        super("El producto con el id " + id + " no existe");
    }
}
