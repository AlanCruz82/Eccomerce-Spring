package com.spring.eccomerce.controller;

import com.spring.eccomerce.service.CategoriaService;
import com.spring.eccomerce.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class HomeController {

    //Dependencia del producto para obtener los productos destacados
    private final ProductoService productoService;
    //Dependencia de categoria para obtener el listado de categorias disponibles
    private final CategoriaService categoriaService;

    @GetMapping("/")
    public String inicio(Model model) {

        model.addAttribute("categorias", categoriaService.obtenerCategorias());

        model.addAttribute("productosDestacados", productoService.obtenerProductosDestacados());

        return "home/index";
    }
}
