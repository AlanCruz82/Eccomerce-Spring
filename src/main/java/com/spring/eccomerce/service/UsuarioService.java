package com.spring.eccomerce.service;

import com.spring.eccomerce.dto.usuario.UsuarioEditRequestDTO;
import com.spring.eccomerce.dto.usuario.UsuarioRequestDTO;
import com.spring.eccomerce.dto.usuario.UsuarioResponseDTO;
import com.spring.eccomerce.dto.usuario.UsuarioResumenDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UsuarioService {

    UsuarioResumenDTO registrar(UsuarioRequestDTO dto);
    List<UsuarioResumenDTO> obtenerTodos();
    Page<UsuarioResponseDTO> obtenerUsuarios(String busqueda, Pageable pageable);
    UsuarioEditRequestDTO obtenerUsuarioEditar(Long id);
    UsuarioResponseDTO actualizarUsuario(Long id, UsuarioEditRequestDTO dto);
    void cambiarEstadoActivo(Long id, boolean activo);
}