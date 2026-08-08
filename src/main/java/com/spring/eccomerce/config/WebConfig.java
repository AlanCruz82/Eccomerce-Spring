package com.spring.eccomerce.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //Referencia para buscar los recursos solicitados de la url /uploads al directorio fisico /uploads
        //de la aplicacion
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }
}
