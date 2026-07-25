package com.spring.eccomerce.service;

import com.spring.eccomerce.dto.categoria.CategoriaConCantidadDTO;
import com.spring.eccomerce.dto.categoria.CategoriaRequestDTO;
import com.spring.eccomerce.dto.categoria.CategoriaResponseDTO;
import com.spring.eccomerce.dto.categoria.CategoriaResumenDTO;

import java.util.List;

public interface CategoriaService {

    CategoriaResponseDTO crearCategoria(CategoriaRequestDTO nuevaCategoria);
    CategoriaResponseDTO actualizarCategoria(Long id, CategoriaRequestDTO nuevaCategoria);
    void eliminarCategoria(Long id);
    List<CategoriaResumenDTO> obtenerCategorias();
    CategoriaResponseDTO obtenerCategoriaPorId(Long id);
    CategoriaRequestDTO obtenerCategoriaEditar(Long id);
    List<CategoriaConCantidadDTO> obtenerTop4CategoriasConMasProductos();
}
