package com.spring.eccomerce.repository;

import com.spring.eccomerce.dto.producto.ProductoResumenDTO;
import com.spring.eccomerce.entity.Producto;
import com.spring.eccomerce.repository.projection.ICategoriaConCantidad;
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

    //Consulta para obtener las 4 categorias con mayor numero de productos registrados
    //Usando la interfaz de CategoriaConCantidad para obtener los datos generados de la consulta
    @Query(
            value = """
                SELECT c.id_categoria AS id, c.nombre AS nombre,
                       COUNT(p.id_producto) AS cantidad
                FROM categorias c
                INNER JOIN productos p ON c.id_categoria = p.id_categoria
                GROUP BY c.id_categoria, c.nombre
                ORDER BY cantidad DESC
                LIMIT 4
            """,
            nativeQuery = true
    )
    List<ICategoriaConCantidad> findTop4CategoriasConMasProductos();
}
