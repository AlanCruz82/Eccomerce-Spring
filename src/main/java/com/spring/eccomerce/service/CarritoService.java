package com.spring.eccomerce.service;

import com.spring.eccomerce.dto.carrito.CarritoDTO;

import java.math.BigDecimal;

public interface CarritoService {

    public CarritoDTO obtenerCarrito();
    public void vaciarCarrito();
    public void agregarProducto(Long idProducto);
    public void eliminarProducto(Long idProducto);
    public void aumentarCantidad(Long idProducto);
    public void disminuirCantidad(Long idProducto);
    public BigDecimal obtenerTotal();
}
