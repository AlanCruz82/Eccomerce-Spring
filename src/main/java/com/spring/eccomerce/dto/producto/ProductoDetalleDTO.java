package com.spring.eccomerce.dto.producto;

import com.spring.eccomerce.dto.categoria.CategoriaResumenDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Setter @Getter
public class ProductoDetalleDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private String urlImagen;
    private Integer existencia;
    private CategoriaResumenDTO categoria;
}
