package com.spring.eccomerce.service;

import com.spring.eccomerce.dto.categoria.CategoriaRequestDTO;
import com.spring.eccomerce.dto.categoria.CategoriaResponseDTO;
import com.spring.eccomerce.dto.categoria.CategoriaResumenDTO;

import java.util.List;

public interface CategoriaService {

    public CategoriaResponseDTO crearCategoria(CategoriaRequestDTO nuevaCategoria);
    public CategoriaResponseDTO actualizarCategoria(Long id, CategoriaRequestDTO nuevaCategoria);
    public void eliminarCategoria(Long id);
    public List<CategoriaResumenDTO> obtenerCategorias();
    public List<CategoriaResumenDTO> obtenerTop4Categorias();
    public CategoriaResponseDTO obtenerCategoriaPorId(Long id);
}
