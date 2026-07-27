package com.spring.eccomerce.service;

import com.spring.eccomerce.dto.usuario.UsuarioRequestDTO;
import com.spring.eccomerce.dto.usuario.UsuarioResumenDTO;

public interface UsuarioService {

    UsuarioResumenDTO registrar(UsuarioRequestDTO dto);
}
