package com.spring.eccomerce.repository;

import com.spring.eccomerce.entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Optional<Producto> findByNombreIgnoreCase(String nombre);
    Page<Producto> findByCategoriaId(Long id, Pageable pagina);
    List<Producto> findByOrderByPrecioDesc();
    List<Producto> findByOrderByPrecioAsc();
}
