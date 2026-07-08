package com.spring.eccomerce.dto.permiso;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter @Getter
public class PermisoResponseDTO {

    private Long id;
    private String nombre;
}
