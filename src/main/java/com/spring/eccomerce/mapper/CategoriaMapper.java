package com.spring.eccomerce.mapper;

import com.spring.eccomerce.dto.categoria.CategoriaRequestDTO;
import com.spring.eccomerce.dto.categoria.CategoriaResponseDTO;
import com.spring.eccomerce.dto.categoria.CategoriaResumenDTO;
import com.spring.eccomerce.entity.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    public CategoriaResponseDTO toDTO(Categoria categoria){
        return CategoriaResponseDTO.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())
                .fechaCreacion(categoria.getFechaCreacion())
                .fechaActualizacion(categoria.getFechaActualizacion())
                .build();
    }

    public Categoria toEntity(CategoriaRequestDTO dto){
        return Categoria.builder().
                nombre(dto.getNombre()).
                build();
    }

    public CategoriaResumenDTO toResumenDTO(Categoria categoria){
        return CategoriaResumenDTO.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())
                .build();
    }

    public CategoriaRequestDTO toRequestDTO(CategoriaResponseDTO responseDTO){
        //Generamos la CategoriaRequest, que va a almacenar la informacion de la categoria que ya existe en la base de datos
        CategoriaRequestDTO requestDTO = new CategoriaRequestDTO();

        //Establecemos la informacion de la categoria
        requestDTO.setNombre(responseDTO.getNombre());

        //Regresamos la categoriaRequest generada
        return requestDTO;
    }
}
