package com.spring.eccomerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    @GetMapping("/nueva")
    public String mostrarFormularioNuevaCategoria(){
        return null;
    }

    @PostMapping
    public String crearCategoria(){
        return null;
    }

    @GetMapping("/actualizar")
    public String mostrarFormularioActualizarCategoria(){
        return null;
    }

    @PostMapping("/{id}")
    public String actualizarCategoria(){
        return null;
    }

    @PostMapping("/{id}/eliminar")
    public String eliminarCategoria(){
        return null;
    }

    /*
    @GetMapping("/{id}")
    public String verDetalleCategoriaPorId(){

    }*/

    @GetMapping
    public String listarCategorias(){
        return null;
    }

}
