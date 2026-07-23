package com.spring.eccomerce.repository;

import com.spring.eccomerce.dto.producto.ProductoResumenDTO;
import com.spring.eccomerce.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>, JpaSpecificationExecutor<Producto> {
    boolean existsByNombreIgnoreCase(String nombre);
    //Consulta para obtener los ultimos 8 productos agregados en la base base de datos (productos destacados)
    List<Producto> findTop8ByOrderByIdDesc();
}
