package com.spring.eccomerce.service.impl;

import com.spring.eccomerce.dto.categoria.CategoriaRequestDTO;
import com.spring.eccomerce.dto.categoria.CategoriaResponseDTO;
import com.spring.eccomerce.dto.categoria.CategoriaResumenDTO;
import com.spring.eccomerce.entity.Categoria;
import com.spring.eccomerce.mapper.CategoriaMapper;
import com.spring.eccomerce.repository.CategoriaRepository;
import com.spring.eccomerce.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

//Contructor para inyectar las depedencias definidas como campos
@RequiredArgsConstructor
@Service
public class CategoriaServiceImpl implements CategoriaService {

    //Dependencia para el manejo de datos
    private final CategoriaRepository categoriaRepository;
    //Dependencia para la construir de dtos-entidades
    private final CategoriaMapper categoriaMapper;

    @Override
    @SneakyThrows //TEMPORAL: para evitar manejo de excepciones en la firma del metodo
    public CategoriaResponseDTO crearCategoria(CategoriaRequestDTO nuevaCategoria) {
        //Validamos si ya existe una categoria con el nombre de la nuevaCategoria que se quiere agregar
        if(categoriaRepository.existsByNombreIgnoreCase(nuevaCategoria.getNombre())){
            throw new Exception("La categoria con el nombre" + nuevaCategoria.getNombre() + " ya esta registrada");
        }

        //Transformamos el dto enviado como parametro en una entidad categoria y la almacenamos en la base de datos
        return categoriaMapper.toDTO(categoriaRepository.save(categoriaMapper.toEntity(nuevaCategoria)));
    }

    @Override
    @SneakyThrows
    public CategoriaResponseDTO actualizarCategoria(Long id, CategoriaRequestDTO nuevaCategoria) {
        //Verificamos si la categoria a actualizar existe en la base de datos
        Categoria categoriaActualizar = categoriaRepository.findById(id).orElseThrow(
                () -> new Exception("La categoria con el id" + id + " no existe")
        );

        //Actualizamos los campos de la categoria almacenada con los de la nuevaCategoria
        categoriaActualizar.setNombre(nuevaCategoria.getNombre());

        //Almacenamos la categoria acutualizada y la regresamos como formato responseDTO
        return categoriaMapper.toDTO(categoriaRepository.save(categoriaActualizar));
    }

    @Override
    @SneakyThrows
    public void eliminarCategoria(Long id) {
        //Verificamos si la categoria a eliminar existe en la base de datos
        Categoria categoriaEliminar = categoriaRepository.findById(id).orElseThrow(
                () -> new Exception("La categoria con el id" + id + " no existe")
        );

        categoriaRepository.deleteById(id);
    }

    /*
    @Override
    @SneakyThrows
    public CategoriaResponseDTO obtenerCategoriaPorId(Long id) {
        //Verificamos si la categoria a obtener existe en la base de datos
        Categoria categoriaId = categoriaRepository.findById(id).orElseThrow(
                () -> new Exception("La categoria con el id" + id + " no existe")
        );

        return categoriaMapper.toDTO(categoriaId);
    }*/

    @Override
    public List<CategoriaResumenDTO> obtenerCategorias() {
        return categoriaRepository.findAll().stream().map(categoriaMapper::toResumenDTO).toList();
    }
}
