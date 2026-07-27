package com.spring.eccomerce.exception;

import com.spring.eccomerce.exception.base.DuplicateResourceException;

public class UsuarioDuplicadoException extends DuplicateResourceException {
    public UsuarioDuplicadoException(String correoElectronico) {
        super("El usuario con el correo electronico " +  correoElectronico + " ya existe");
    }
}
