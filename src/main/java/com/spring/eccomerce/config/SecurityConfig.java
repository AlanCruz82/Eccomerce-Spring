package com.spring.eccomerce.config;

import com.spring.eccomerce.service.impl.security.UserDetailsImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //Cadena de filtros por los que va a pasar cualquier peticion que se haga por el cliente
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        //Configuracion de los filtros que se van a aplicar y los recursos disponibles y privados
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/login", "/registro",
                                "/css/**", "/js/**", "/images/**", "/uploads/productos/**").permitAll()
                        .requestMatchers("/", "/productos", "/productos/**",
                                "/categorias", "/carrito/**").permitAll()
                        .requestMatchers("/pedidos").hasRole("ADMIN")
                        .requestMatchers("/pedidos/usuario", "/pedidos/**").authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .permitAll()
                );
        return http.build();
    }

    //Componente que va a delegar la tarea al provedor de autenticacion para validar la autenticacion del usuario
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    //Provedor que de autenticacion que va a validar la existencia del usuario en la bd y el valor de su contrasena
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsImpl userDetailsImpl) {
        //Generamos la instancia del provedorDAO que se va a encargar de autenticar y autorizar el usuario pasandole como argumento
        //la implementacion del userDetails que hemos hecho
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider(userDetailsImpl);
        //Establecemos el algoritmo de encpriptacion que va a utilizar para obtener el valor hash de la contrasena
        daoProvider.setPasswordEncoder(new BCryptPasswordEncoder());

        return daoProvider;
    }

    //Componente para validar el valor hash de la contrasena con el valor enviado por el cliente
    @Bean
    public PasswordEncoder passwordEncoder() {
        //Instancia del algoritmo de encriptacion que va a obtener el valor hash de la contrasena
        return new BCryptPasswordEncoder();
    }
}
