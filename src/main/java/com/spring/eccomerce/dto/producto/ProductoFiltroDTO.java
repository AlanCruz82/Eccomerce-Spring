package com.spring.eccomerce.dto.producto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter @Getter
public class ProductoFiltroDTO {

    private Long categoriaId;
    private String nombre;
    private Double precioMinimo;
    private Double precioMaximo;
    private Integer existencia = 0;
}
