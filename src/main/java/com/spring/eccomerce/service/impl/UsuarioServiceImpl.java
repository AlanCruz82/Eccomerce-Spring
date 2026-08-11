package com.spring.eccomerce.service.impl;

import com.spring.eccomerce.dto.usuario.UsuarioEditRequestDTO;
import com.spring.eccomerce.dto.usuario.UsuarioRequestDTO;
import com.spring.eccomerce.dto.usuario.UsuarioResponseDTO;
import com.spring.eccomerce.dto.usuario.UsuarioResumenDTO;
import com.spring.eccomerce.entity.Rol;
import com.spring.eccomerce.entity.Usuario;
import com.spring.eccomerce.entity.enums.NombreRol;
import com.spring.eccomerce.exception.RolNotFoundException;
import com.spring.eccomerce.exception.UsuarioDuplicadoException;
import com.spring.eccomerce.exception.UsuarioNotFoundException;
import com.spring.eccomerce.mapper.UsuarioMapper;
import com.spring.eccomerce.repository.RolRepository;
import com.spring.eccomerce.repository.UsuarioRepository;
import com.spring.eccomerce.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        if(usuarioRepository.existsByCorreoElectronico(dto.getCorreoElectronico())) {
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
        //Solo obtenemos los usuarios con rol CLIENTE para que el administrador no vea a los demas administradores
        return usuarioRepository.findByRolNombre(NombreRol.CLIENTE).stream()
                .map(usuarioMapper::toResumenDTO)
                .toList();
    }

    @Override
    public Page<UsuarioResponseDTO> obtenerUsuarios(String busqueda, Pageable pageable) {
        //Solo obtenemos los usuarios con rol CLIENTE (los administradores no son gestionables)
        return usuarioRepository.obtenerClientes(NombreRol.CLIENTE, busqueda, pageable)
                .map(usuarioMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioEditRequestDTO obtenerUsuarioEditar(Long id) {
        //Solo se gestionan usuarios con rol CLIENTE
        Usuario usuario = verificarCliente(id);
        return usuarioMapper.toEditRequestDTO(usuario);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioEditRequestDTO dto) {
        //Solo se gestionan usuarios con rol CLIENTE
        Usuario usuario = verificarCliente(id);

        //Si el correo cambia, validamos que el nuevo correo no pertenezca a otro usuario
        if (usuarioRepository.existsByCorreoElectronico(dto.getCorreoElectronico())
                && !dto.getCorreoElectronico().equals(usuario.getCorreoElectronico())) {
            throw new UsuarioDuplicadoException(dto.getCorreoElectronico());
        }

        //El rol del usuario no es editable, se conserva el rol actual
        //Actualizamos los datos del usuario con los enviados en el dto
        usuario.setNombre(dto.getNombre());
        usuario.setCorreoElectronico(dto.getCorreoElectronico());
        usuario.setTelefono(dto.getTelefono());
        usuario.setDireccionEnvio(dto.getDireccionEnvio());
        usuario.setActivo(dto.getActivo());

        //Guardamos el usuario actualizado y lo regresamos en su formato responseDTO
        return usuarioMapper.toResponseDTO(usuarioRepository.save(usuario));
    }

    @Override
    @Transactional
    public void cambiarEstadoActivo(Long id, boolean activo) {
        //Solo se gestionan usuarios con rol CLIENTE
        Usuario usuario = verificarCliente(id);
        usuario.setActivo(activo);
        usuarioRepository.save(usuario);
    }

    //Metodo para validar que el usuario con el id enviado exista y tenga rol CLIENTE
    private Usuario verificarCliente(Long id) {
        return usuarioRepository.findById(id)
                .filter(u -> u.getRol().getNombre() == NombreRol.CLIENTE)
                .orElseThrow(() -> new UsuarioNotFoundException(id));
    }
}