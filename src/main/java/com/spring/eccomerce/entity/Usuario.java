package com.spring.eccomerce.entity;

import com.spring.eccomerce.entity.base.Bitacora;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Entity
@Table(name = "usuarios")
public class Usuario extends Bitacora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(name = "correo_electronico", nullable = false, length = 250, unique = true)
    private String correoElectronico;

    @Column(nullable = false, length = 100)
    private String contrasena;

    @Column(length = 16)
    private String telefono;

    @Column(name = "direccion_envio", nullable = false, length = 150)
    private String direccionEnvio;

    private Boolean activo = true;

    //Carga perezosa, para no traer todos los roles de todos los usuarios al consultarlo
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;
}
