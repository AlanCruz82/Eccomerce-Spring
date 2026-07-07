package com.spring.eccomerce.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class LoginRequestDTO {

    @NotBlank(message = "El correo electronico del usuario no puede ser vacio")
    @Size(max = 250, message = "El correo del usuario no puede tener mas de 250 caracteres")
    private String correoElectronico;

    @NotBlank(message = "La contrasena del usuario no puede ser vacio")
    @Size(max = 100, message = "La contrasena del usuario no puede tener mas de 100 caracteres")
    private String contrasena;
}
