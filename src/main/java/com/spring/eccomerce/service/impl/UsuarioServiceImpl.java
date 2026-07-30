package com.spring.eccomerce.service.impl;

import com.spring.eccomerce.dto.usuario.UsuarioRequestDTO;
import com.spring.eccomerce.dto.usuario.UsuarioResumenDTO;
import com.spring.eccomerce.entity.Rol;
import com.spring.eccomerce.entity.Usuario;
import com.spring.eccomerce.entity.enums.NombreRol;
import com.spring.eccomerce.exception.RolNotFoundException;
import com.spring.eccomerce.exception.UsuarioDuplicadoException;
import com.spring.eccomerce.mapper.UsuarioMapper;
import com.spring.eccomerce.repository.RolRepository;
import com.spring.eccomerce.repository.UsuarioRepository;
import com.spring.eccomerce.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioServiceImpl implements UsuarioService {

    //Dependencia para consultar la capa de datos de usuarios
    private final UsuarioRepository usuarioRepository;
    //Dependencia para obtener el rol del usuario en la base de datos
    private final RolRepository rolRepository;
    //Dependencia para convertir la requestDTO a entidad y viceversa
    private final UsuarioMapper usuarioMapper;
    //Dependencia de la configuracion de seguridad para encriptar la contrasena del usuario
    private final PasswordEncoder passwordEncoder;
    //Rol con el que se va a crear cada nuevo usuario
    private final NombreRol rolUsuario = NombreRol.CLIENTE;

    @Override
    public UsuarioResumenDTO registrar(UsuarioRequestDTO dto) {

        //Validamos si el nuevo usuario que se quiere registrar ya existe en la base de datos
        if(usuarioRepository.existsByCorreoElectronico(dto.     getCorreoElectronico())) {
            throw new UsuarioDuplicadoException(dto.getCorreoElectronico());
        }

        //Obtenemos el rol CLIENTE de la base de datos
        Rol rol = rolRepository.findByNombre(rolUsuario).orElseThrow(
                () -> new RolNotFoundException(rolUsuario.name())
        );

        //Converitmos el usuarioDTO a una entidad usuario
        Usuario usuario = usuarioMapper.toEntity(dto);

        //Obtenemos el valor hash de la contrasena enviada usando el bean de BCryp y lo establecemos como la
        //contrasena del usuario
        usuario.setContrasena(passwordEncoder.encode(dto.getContrasena()));

        //Establecemos el rol del nuevo usuario
        usuario.setRol(rol);

        //Guardamos el nuevo usuario en la base de datos y regresamos el usuario en su formato resumenDTO
        return usuarioMapper.toResumenDTO(usuarioRepository.save(usuario));
    }

    @Override
    public List<UsuarioResumenDTO> obtenerTodos() {
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toResumenDTO)
                .toList();
    }
}
