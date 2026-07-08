package com.spring.eccomerce.dto.usuario;

import com.spring.eccomerce.dto.rol.RolReponseDTO;
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
    private RolReponseDTO rol;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}
