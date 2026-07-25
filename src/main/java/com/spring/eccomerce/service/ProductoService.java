package com.spring.eccomerce.service;

import com.spring.eccomerce.dto.producto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductoService {

    //Metodo unico que va a implementar los filtros definidos (categoria/nombre/precioMinimo/precioMaximo)
    Page<ProductoResumenDTO> obtenerProductos(ProductoFiltroDTO filtroDTO, Pageable pagina);
    ProductoDetalleDTO obtenerProductoPorId(Long id);
    ProductoResumenDTO crearProducto(ProductoRequestDTO producto);
    void actualizarProducto(Long id, ProductoRequestDTO productoActualizar);
    List<ProductoResumenDTO> obtenerProductosDestacados();
    void eliminarProducto(Long id);
    ProductoRequestDTO obtenerProductoEditar(Long id);
}
