package com.spring.eccomerce.service;

import com.spring.eccomerce.dto.producto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductoService {

    //Metodo unico que va a implementar los filtros definidos (categoria/nombre/precioMinimo/precioMaximo)
    public Page<ProductoResumenDTO> obtenerProductos(ProductoFiltroDTO filtroDTO, Pageable pagina);
    public ProductoDetalleDTO obtenerProductoPorId(Long id);
    public ProductoResumenDTO crearProducto(ProductoRequestDTO producto);
    public ProductoResumenDTO actualizarProducto(Long id, ProductoRequestDTO productoActualizar);
    public void eliminarProducto(Long id);
}
