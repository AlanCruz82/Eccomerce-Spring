package com.spring.eccomerce.dto.checkout;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class CheckoutDTO {

    private String nombreCliente;
    private String telefono;
    private String direccionEnvio;
    private String ciudad;
    private String codigoPostal;
    private String referencia;
}
