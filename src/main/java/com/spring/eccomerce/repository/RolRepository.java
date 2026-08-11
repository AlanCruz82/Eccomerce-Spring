package com.spring.eccomerce.repository;

import com.spring.eccomerce.entity.Rol;
import com.spring.eccomerce.entity.enums.NombreRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

    Optional<Rol> findByNombre(NombreRol nombre);
}
