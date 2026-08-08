package com.spring.eccomerce.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "storage")
@Component
@Setter @Getter
public class StorageProperties {

    //Valor que se va a tomar del application.yaml dentro del segmento definido con el prefix (directorio de almacenamiento)
    private String uploadDir;
    //Url donde vamos a servir las imagenes almacenadas
    private String uploadUrl;
}
