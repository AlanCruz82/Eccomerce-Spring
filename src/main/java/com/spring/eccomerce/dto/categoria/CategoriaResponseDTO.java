package com.spring.eccomerce.dto.categoria;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Setter @Getter
public class CategoriaResponseDTO {

    private Long id;
    private String nombre;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}
