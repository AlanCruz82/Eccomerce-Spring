package com.spring.eccomerce.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class UsuarioRequestDTO {

    @NotBlank(message = "El nombre del usuario no puede ser vacio")
    @Size(max = 100, message = "El nombre del usuario no puede tener mas de 100 caracteres")
    private String nombre;

    @NotBlank(message = "El correo electronico del usuario no puede ser vacio")
    @Size(max = 250, message = "El correo del usuario no puede tener mas de 250 caracteres")
    private String correoElectronico;

    @NotBlank(message = "La contrasena del usuario no puede ser vacio")
    @Size(max = 100, message = "La contrasena del usuario no puede tener mas de 100 caracteres")
    private String contrasena;

    @NotBlank(message = "El telefono del usuario no puede ser vacio")
    @Size(max = 16, message = "El telefono del usuario no puede tener mas de 16 caracteres")
    private String telefono;

    @NotBlank(message = "La direccion de envio del usuario no puede ser vacio")
    @Size(max = 150, message = "La direccion de envio del usuario no puede tener mas de 150 caracteres")
    private String direccionEnvio;

}
