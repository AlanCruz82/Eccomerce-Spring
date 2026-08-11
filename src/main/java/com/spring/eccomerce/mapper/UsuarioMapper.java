package com.spring.eccomerce.mapper;

import com.spring.eccomerce.dto.usuario.UsuarioEditRequestDTO;
import com.spring.eccomerce.dto.usuario.UsuarioRequestDTO;
import com.spring.eccomerce.dto.usuario.UsuarioResponseDTO;
import com.spring.eccomerce.dto.usuario.UsuarioResumenDTO;
import com.spring.eccomerce.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioRequestDTO dto){

        //Asignamos cada campo del dto a la entidad
        //EXCEPTO LA PASSWORD Y ROL, ya que eso lo vamos a hacer en la capa de servicio
        return Usuario.builder()
                .nombre(dto.getNombre())
                .correoElectronico(dto.getCorreoElectronico())
                .telefono(dto.getTelefono())
                .direccionEnvio(dto.getDireccionEnvio())
                .activo(true)
                .build();
    }

    public UsuarioResumenDTO toResumenDTO(Usuario usuario){
        return UsuarioResumenDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .correoElectronico(usuario.getCorreoElectronico())
                .build();
    }

    public UsuarioResponseDTO toResponseDTO(Usuario usuario){
        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .correoElectronico(usuario.getCorreoElectronico())
                .telefono(usuario.getTelefono())
                .direccionEnvio(usuario.getDireccionEnvio())
                .activo(usuario.getActivo())
                .rol(usuario.getRol().getNombre())
                .fechaCreacion(usuario.getFechaCreacion())
                .fechaActualizacion(usuario.getFechaActualizacion())
                .build();
    }

    public UsuarioEditRequestDTO toEditRequestDTO(Usuario usuario){
        UsuarioEditRequestDTO dto = new UsuarioEditRequestDTO();
        dto.setNombre(usuario.getNombre());
        dto.setCorreoElectronico(usuario.getCorreoElectronico());
        dto.setTelefono(usuario.getTelefono());
        dto.setDireccionEnvio(usuario.getDireccionEnvio());
        dto.setRol(usuario.getRol().getNombre());
        dto.setActivo(usuario.getActivo());
        return dto;
    }
}
