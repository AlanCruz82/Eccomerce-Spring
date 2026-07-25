package com.spring.eccomerce.dto.categoria;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class CategoriaConCantidadDTO {

    private Long id;
    private String nombre;
    private Long cantidad;
}
