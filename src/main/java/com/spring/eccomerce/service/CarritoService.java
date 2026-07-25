package com.spring.eccomerce.service;

import com.spring.eccomerce.dto.carrito.CarritoDTO;

import java.math.BigDecimal;

public interface CarritoService {

    CarritoDTO obtenerCarrito();
    void vaciarCarrito();
    void agregarProducto(Long idProducto);
    void eliminarProducto(Long idProducto);
    void aumentarCantidad(Long idProducto);
    void disminuirCantidad(Long idProducto);
    BigDecimal obtenerTotal();
}
