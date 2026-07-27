package com.spring.eccomerce.service.impl.security;

import com.spring.eccomerce.entity.Usuario;
import com.spring.eccomerce.exception.UsuarioNotFoundException;
import com.spring.eccomerce.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetailsService {

    //Depedencia del repositorio del usuario que vamos a utilizar para obtener el usuario por el correo enviado
    private final UsuarioRepository usuarioRepository;

    //Metodo sobreescrito de la interfaz UserDetailsService, que va a valir la existencia del usuario en la base de datos
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Validamos si el usuario con el username enviado como parametro existe en la base de datos
        Usuario usuario = usuarioRepository.findByCorreoElectronico(username).orElseThrow(
                () -> new UsuarioNotFoundException(username)
        );

        //Regresamos el usuario encontrado en la base datos, convertido en un UsuarioSecurity, que es el que entiede
        //spring security
        return new UsuarioSecurity(usuario);
    }
}
