package com.spring.eccomerce.repository;

import com.spring.eccomerce.entity.Rol;
import com.spring.eccomerce.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByNombreIgnoreCase(String nombre);
    Optional<Usuario> findByCorreoElectronico(String correo);
    boolean existsByCorreoElectronico(String correo);
    Optional<Usuario> findByRolId(Rol idRol);
}
