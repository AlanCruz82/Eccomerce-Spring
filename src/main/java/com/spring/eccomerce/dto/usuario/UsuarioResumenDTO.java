package com.spring.eccomerce.dto.usuario;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter @Getter
public class UsuarioResumenDTO {

    private Long id;
    private String nombre;
    private String correElectronico;
}
