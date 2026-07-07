package com.spring.eccomerce.dto.producto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter @Getter
public class ProductoRequestDTO {

    @NotBlank(message = "El nombre del producto no puede estar vacio")
    @Size(min = 1, max = 100, message = "El producto no puede tener menos de 1 caracter ni mas de 100 caracteres")
    private String nombre;

    @Size(min = 5, max = 250, message = "La descripcion del producto no puede tener menos de 5 caracteres ni mas de 250 caracteres")
    private String descripcion;

    @Min(value = 1, message = "El precio del producto no puede ser menor a 1")
    private BigDecimal precio;

    @Min(value = 0, message = "La existencia del producto no puede ser menor a 0 unidades")
    private Integer existencia;

    @NotBlank(message = "La imagen del producto no puede estar vacia")
    @Size(max = 250, message = "La url de la imagen del producto no puede ser mayor a 250 caracteres")
    private String urlImagen;

    @NotNull(message = "La categoria del producto no puede ser nula")
    private Long idCategoria;
}
