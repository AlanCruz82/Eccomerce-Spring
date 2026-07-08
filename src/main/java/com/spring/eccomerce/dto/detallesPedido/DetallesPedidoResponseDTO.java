package com.spring.eccomerce.dto.detallesPedido;

import com.spring.eccomerce.dto.producto.ProductoResumenDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Setter @Getter
public class DetallesPedidoResponseDTO {

    private Long id;
    private ProductoResumenDTO producto;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
}
