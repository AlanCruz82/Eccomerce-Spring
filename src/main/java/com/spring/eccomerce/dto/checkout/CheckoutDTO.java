package com.spring.eccomerce.dto.checkout;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class CheckoutDTO {

    @NotBlank(message = "El nombre del cliente no puede estar vacio")
    @Size(max = 150, message = "El nombre del cliente no puede tener mas de 150 caracteres")
    private String nombreCliente;

    @NotBlank(message = "El telefono no puede ser vacio")
    @Size(max = 16, message = "El telefono de contacto no puede tener mas de 16 caracteres")
    @Pattern(regexp = "^[0-9]{10}$", message = "Ingrese un teléfono válido.")
    private String telefono;

    @NotBlank(message = "La direccion de envio del no puede estar vacio")
    @Size(max = 150, message = "La direccion de envio no puede tener mas de 150 caracteres")
    private String direccionEnvio;

    @NotBlank(message = "La ciudad no puede estar vacio")
    @Size(max = 150, message = "La ciudad puede tener mas de 150 caracteres")
    private String ciudad;

    @NotBlank(message = "El codigo postal no puede ser vacio")
    @Size(max = 5, message = "El codigo postal no puede tener mas de 5 digitos")

    private String codigoPostal;

    private String referencia;
}
