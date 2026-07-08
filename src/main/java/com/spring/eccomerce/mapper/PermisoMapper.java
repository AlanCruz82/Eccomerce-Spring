package com.spring.eccomerce.mapper;

import com.spring.eccomerce.dto.permiso.PermisoResponseDTO;
import com.spring.eccomerce.entity.Permiso;
import org.springframework.stereotype.Component;

@Component
public class PermisoMapper {

    public PermisoResponseDTO toDTO(Permiso permiso){
        return PermisoResponseDTO.builder()
                .id(permiso.getId())
                .nombre(permiso.getNombre())
                .build();
    }
}
