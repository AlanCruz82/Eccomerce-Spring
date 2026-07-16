package com.spring.eccomerce.dto.carrito;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter @Getter
public class ItemCarritoDTO {

    private Long idProducto;
    private String nombre;
    private BigDecimal precio;
    private Integer cantidad;
    private String urlImagen;

    public BigDecimal getSubtotal(){
        return precio.multiply(BigDecimal.valueOf(cantidad));
    }
}
