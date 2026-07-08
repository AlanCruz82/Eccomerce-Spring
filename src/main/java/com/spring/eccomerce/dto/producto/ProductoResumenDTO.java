package com.spring.eccomerce.dto.producto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Setter @Getter
public class ProductoResumenDTO {

    private Long id;
    private String nombre;
    private BigDecimal precio;
    private String urlImagen;
}
