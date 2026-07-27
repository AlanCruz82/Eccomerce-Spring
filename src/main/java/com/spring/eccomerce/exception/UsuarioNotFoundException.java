package com.spring.eccomerce.exception;

import com.spring.eccomerce.exception.base.ResourceNotFoundException;

public class UsuarioNotFoundException extends ResourceNotFoundException {
    public UsuarioNotFoundException(Long id) {
        super("El usuario con el id " + id + " no existe");
    }

    public UsuarioNotFoundException(String correoElectronico){
        super("El usuario con el correo electronico " + correoElectronico + " no existe");
    }
}
