package com.spring.eccomerce.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {

    String guardar(MultipartFile file);
    void eliminar(String nombreArchivo);
}
