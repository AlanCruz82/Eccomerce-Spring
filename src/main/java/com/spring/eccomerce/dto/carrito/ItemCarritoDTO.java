package com.spring.eccomerce.dto.carrito;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter @Getter
public class ItemCarritoDTO {

    private Long idProducto;
    private String nombre;
    private BigDecimal precio;
    //Existencia/cantidad deseada por el usuario
    private Integer cantidad;
    //Existencia de la entidad producto, guardada en la base de datos
    private Integer cantidadDisponible;
    private String urlImagen;

    public BigDecimal getSubtotal(){
        return precio.multiply(BigDecimal.valueOf(cantidad));
    }

    public boolean puedeAumentarCantidad(){
        return cantidad < cantidadDisponible;
    }
}
