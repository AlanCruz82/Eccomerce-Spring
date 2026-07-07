package com.spring.eccomerce.entity;

import com.spring.eccomerce.entity.base.Bitacora;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "productos")
public class Producto extends Bitacora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long id;

    @Column(nullable = false ,length = 100)
    private String nombre;

    @Column(length = 250)
    private String descripcion;

    //Permitiremos un precio con 9 enteros como maximo y dos numeros despues del punto
    @Digits(integer = 9,fraction = 2)
    @Column(nullable = false)
    private BigDecimal precio;

    @Min(0)
    @Column(nullable = false)
    private Integer existencia;

    @Column(name = "url_imagen", nullable = false, length = 250)
    private String urlImagen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;
}
