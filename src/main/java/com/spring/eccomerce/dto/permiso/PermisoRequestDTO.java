package com.spring.eccomerce.dto.permiso;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class PermisoRequestDTO {

    @NotBlank(message = "El nombre del permiso no puede estar vacio")
    private String nombre;
}
