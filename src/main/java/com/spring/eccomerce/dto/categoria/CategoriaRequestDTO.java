package com.spring.eccomerce.dto.categoria;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class CategoriaRequestDTO {

    @NotBlank(message = "El nombre de la categoria no puede estar vacio")
    @Size(min = 1, max = 100, message = "El nombre no puede tener menos de 1 caracter ni mas de 100 caracteres")
    private String nombre;
}
