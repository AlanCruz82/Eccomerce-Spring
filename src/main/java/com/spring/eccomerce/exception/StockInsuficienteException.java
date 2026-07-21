package com.spring.eccomerce.exception;

public class StockInsuficienteException extends RuntimeException {
    public StockInsuficienteException(Long idProducto) {
        super("Las existencias para el producto con id " + idProducto + " se han agotado");
    }
}
