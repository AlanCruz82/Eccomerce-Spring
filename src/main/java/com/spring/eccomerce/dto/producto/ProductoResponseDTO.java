package com.spring.eccomerce.dto.producto;

import com.spring.eccomerce.dto.categoria.CategoriaResumenDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Setter @Getter
public class ProductoResponseDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer existencia;
    private String urlImagen;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private CategoriaResumenDTO categoria;

}
