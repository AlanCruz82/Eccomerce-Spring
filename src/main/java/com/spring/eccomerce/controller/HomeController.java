package com.spring.eccomerce.controller;

import com.spring.eccomerce.service.CategoriaService;
import com.spring.eccomerce.service.ProductoService;
import com.spring.eccomerce.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Controller
public class HomeController {

    //Dependencia del producto para obtener los productos destacados
    private final ProductoService productoService;
    //Dependencia de categoria para obtener el listado de categorias disponibles
    private final CategoriaService categoriaService;
    private final StorageService storageService;

    @GetMapping("/")
    public String inicio(Model model) {

        model.addAttribute("categoriasPopulares", categoriaService.obtenerTop4CategoriasConMasProductos());

        model.addAttribute("productosDestacados", productoService.obtenerProductosDestacados());

        return "home/index";
    }

    @PostMapping("/test")
    public String subirImagen(
            @RequestParam("imagen") MultipartFile imagen) {

        System.out.println("ENTRÓ AL CONTROLADOR");
        System.out.println("Archivo: " + imagen.getOriginalFilename());

        return storageService.guardar(imagen);
    }
}
