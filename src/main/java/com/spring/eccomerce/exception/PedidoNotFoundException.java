package com.spring.eccomerce.exception;

import com.spring.eccomerce.exception.base.ResourceNotFoundException;

public class PedidoNotFoundException extends ResourceNotFoundException {
    public PedidoNotFoundException(Long id) {
        super("El pedido con el id " + id + " no existe");
    }
}
