package com.spring.eccomerce.dto.rol;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RolRequestDTO {

    @NotBlank(message = "El nombre del rol no puede estar vacio")
    private String nombre;
}
