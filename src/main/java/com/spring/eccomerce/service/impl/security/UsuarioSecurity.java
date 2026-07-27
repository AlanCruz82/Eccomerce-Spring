package com.spring.eccomerce.service.impl.security;

import com.spring.eccomerce.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

//Inyeccion del usuario por constructor
@RequiredArgsConstructor
public class UsuarioSecurity implements UserDetails {

    //Entidad del usuario de nuestra base de datos del cual vamos a obtener las propiedades para almacenarlas en el
    //contexto de seguridad
    private final Usuario usuario;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //Convertimos el rol del usuario en SimpleGrnatedAuthority que es la autorizacion que entiende spring security
        return List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().getNombre().name()));
    }

    @Override
    public @Nullable String getPassword() {
        return usuario.getContrasena();
    }

    @Override
    public String getUsername() {
        return usuario.getCorreoElectronico();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return usuario.getActivo();
    }

    public Usuario getUsuario(){
        return this.usuario;
    }
}
