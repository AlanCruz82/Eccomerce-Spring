package com.spring.eccomerce.dto.usuario;

import com.spring.eccomerce.dto.rol.RolReponseDTO;

import java.time.LocalDateTime;

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
