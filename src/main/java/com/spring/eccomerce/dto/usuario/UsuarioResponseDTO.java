package com.spring.eccomerce.dto.usuario;

import com.spring.eccomerce.entity.enums.NombreRol;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Setter @Getter
public class UsuarioResponseDTO {

    private Long id;
    private String nombre;
    private String correoElectronico;
    private String telefono;
    private String direccionEnvio;
    private Boolean activo;
    private NombreRol rol;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}