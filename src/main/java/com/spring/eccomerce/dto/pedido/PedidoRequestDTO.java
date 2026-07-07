package com.spring.eccomerce.dto.pedido;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class PedidoRequestDTO {

    @NotBlank(message = "La direccion de envio del usuario no puede ser vacio")
    @Size(max = 150, message = "La direccion de envio del usuario no puede tener mas de 150 caracteres")
    private String direccionEnvio;
}
