package com.spring.eccomerce.dto.detallesPedido;

import com.spring.eccomerce.dto.producto.ProductoResumenDTO;

import java.math.BigDecimal;

public class DetallesPedidoResponseDTO {

    private Long id;
    private ProductoResumenDTO producto;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
}
