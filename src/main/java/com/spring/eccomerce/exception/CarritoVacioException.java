package com.spring.eccomerce.exception;

public class CarritoVacioException extends RuntimeException {
    public CarritoVacioException() {
        super("El carrito de la sesion no contiene ningun item");
    }
}
