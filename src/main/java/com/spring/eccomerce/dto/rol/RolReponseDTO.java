package com.spring.eccomerce.dto.rol;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter @Getter
public class RolReponseDTO {

    private Long id;
    private String nombre;
}
