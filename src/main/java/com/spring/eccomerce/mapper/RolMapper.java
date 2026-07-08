package com.spring.eccomerce.mapper;

import com.spring.eccomerce.dto.rol.RolReponseDTO;
import com.spring.eccomerce.entity.Rol;
import org.springframework.stereotype.Component;

@Component
public class RolMapper {

    public RolReponseDTO toDTO(Rol rol){
        return RolReponseDTO.builder()
                .id(rol.getId())
                .nombre(rol.getNombre())
                .build();
    }

}
