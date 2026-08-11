package com.spring.eccomerce.dto.producto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Setter @Getter
public class ProductoFiltroDTO {

    private Long categoriaId;
    private String nombre;
    private BigDecimal precioMinimo;
    private BigDecimal precioMaximo;
    private Integer existencia;
}
