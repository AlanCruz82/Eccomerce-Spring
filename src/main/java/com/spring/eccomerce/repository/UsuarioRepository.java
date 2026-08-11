package com.spring.eccomerce.repository;

import com.spring.eccomerce.entity.Rol;
import com.spring.eccomerce.entity.Usuario;
import com.spring.eccomerce.entity.enums.NombreRol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByNombreIgnoreCase(String nombre);
    //Consulta personalizada para obtener el usuario y el rol juntos y evitar una LazyInitializationExcepcion
    @Query("""
        SELECT u
        FROM Usuario u
        JOIN FETCH u.rol
        WHERE u.correoElectronico = :correo
    """)
    Optional<Usuario> findByCorreoElectronico(String correo);
    boolean existsByCorreoElectronico(String correo);
    Optional<Usuario> findByRolId(Rol idRol);
    List<Usuario> findByRolNombre(NombreRol rol);

    //Consulta de clientes (rol CLIENTE) con busqueda por nombre o correo. Une el rol para evitar LazyInitializationExcepcion
    @Query("""
        SELECT u
        FROM Usuario u
        JOIN FETCH u.rol
        WHERE u.rol.nombre = :rol
          AND (:busqueda IS NULL OR :busqueda = ''
               OR LOWER(u.nombre) LIKE LOWER(CONCAT('%', :busqueda, '%'))
               OR LOWER(u.correoElectronico) LIKE LOWER(CONCAT('%', :busqueda, '%')))
    """)
    Page<Usuario> obtenerClientes(@Param("rol") NombreRol rol, @Param("busqueda") String busqueda, Pageable pageable);
}