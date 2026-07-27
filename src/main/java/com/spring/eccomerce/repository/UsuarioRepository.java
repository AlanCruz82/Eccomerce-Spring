    package com.spring.eccomerce.repository;

    import com.spring.eccomerce.entity.Rol;
    import com.spring.eccomerce.entity.Usuario;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.stereotype.Repository;

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
    }
