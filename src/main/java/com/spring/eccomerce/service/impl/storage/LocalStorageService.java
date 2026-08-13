package com.spring.eccomerce.service.impl.storage;

import com.spring.eccomerce.config.StorageProperties;
import com.spring.eccomerce.exception.ImagenExcedeTamanoException;
import com.spring.eccomerce.exception.StorageExcepcion;
import com.spring.eccomerce.service.StorageService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocalStorageService implements StorageService {

    //Dependencia del valor asignado en el yaml, con el que vamos a realizar las operaciones de guardado y eliminacion
    private final StorageProperties properties;
    //Atributo para manejar las rutas de la imagen
    private Path root;

    //Inicializacion del directorio de imagenes, si no existe se crea y si ya existe NO lo sobreescribe
    @PostConstruct
    public void init() {
        //Definicion de la ruta donde vamos a almacenar las imagenes
        root = Paths.get(properties.getUploadDir())
                .toAbsolutePath()
                .normalize();

        try {
            //Creacion del directorio
            Files.createDirectories(root);
        }catch (IOException e){
            throw new StorageExcepcion("Error al intentar crear el directorio de imagenes", e);
        }
    }

    @Override
    public String guardar(MultipartFile file) {

        //Validamos si la imagen enviada no tiene contenido
        if (file.isEmpty()){
            throw new StorageExcepcion("La imagen enviada esta vacia");
        }

        //Validamos si el peso de la imagen supera el tamaño maximo permitido
        long maxBytes = properties.getMaxFileSize() * 1024L * 1024L;
        if (file.getSize() > maxBytes) {
            throw new ImagenExcedeTamanoException(properties.getMaxFileSize());
        }

        try {
            //Validamos la extension del archivo enviado como parametro
            String extension = StringUtils.getFilenameExtension(
                    file.getOriginalFilename()
            );

            //Genereamos el nombre de la imagen, usando UUID como identificador agregando el nombre original de la imagen recibida
            String nombreArchivo = UUID.randomUUID().toString().concat(".").concat(extension);

            //Agregamos la imagen con su nombre generado al directorio de imagenes
            Path destino = root.resolve(nombreArchivo);

            //Pegamos la imagen dentro del directorio de imagenes
            Files.copy(
                    file.getInputStream(),
                    destino,
                    StandardCopyOption.REPLACE_EXISTING
            );

            //Regresamos el nombre generado para la imagen almacenada
            return properties.getUploadUrl().concat("/").concat(nombreArchivo);

        } catch (IOException e) {
            //Si no se puede procesar la ruta o existe algun problema, avisamos al usuario
            throw new StorageExcepcion("No fue posible guardar la imagen", e);
        }
    }

    @Override
    public void eliminar(String urlImagen){
        //Obtenemos el nombre de la imagen de la url enviada como parametro
        String nombreArchivo = StringUtils.getFilename(urlImagen);

        //Si el nombre del archivo es nulo, avisamos al usuario
        if (nombreArchivo == null) {
            throw new StorageExcepcion("La URL de la imagen no es válida");
        }

        try {
            //Eliminamos la imagen del directorio de las imagenes de los productos
            Files.deleteIfExists(root.resolve(nombreArchivo));

        } catch (IOException e) {
            throw new StorageExcepcion("Error al eliminar la imagen", e);
        }
    }
}