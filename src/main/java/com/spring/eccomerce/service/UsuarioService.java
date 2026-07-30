package com.spring.eccomerce.service;

import com.spring.eccomerce.dto.usuario.UsuarioRequestDTO;
import com.spring.eccomerce.dto.usuario.UsuarioResumenDTO;

import java.util.List;

public interface UsuarioService {

    UsuarioResumenDTO registrar(UsuarioRequestDTO dto);
    List<UsuarioResumenDTO> obtenerTodos();
}
