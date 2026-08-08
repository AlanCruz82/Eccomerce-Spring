package com.spring.eccomerce.controller.admin;

import com.spring.eccomerce.dto.producto.ProductoRequestDTO;
import com.spring.eccomerce.dto.producto.ProductoResumenDTO;
import com.spring.eccomerce.service.CategoriaService;
import com.spring.eccomerce.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/productos")
@RequiredArgsConstructor
public class AdminProductoController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("producto", new ProductoRequestDTO());
        model.addAttribute("categorias", categoriaService.obtenerCategorias());
        return "producto/formulario";
    }

    @PostMapping
    public String crearProducto(@Valid @ModelAttribute("producto") ProductoRequestDTO requestDTO,
                                BindingResult bindingResult, Model model,
                                @RequestParam(name = "imagen") MultipartFile imagen) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categorias", categoriaService.obtenerCategorias());
            return "producto/formulario";
        }
        ProductoResumenDTO creado = productoService.crearProducto(requestDTO, imagen);
        return "redirect:/productos/" + creado.getId();
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("producto", productoService.obtenerProductoEditar(id));
        model.addAttribute("categorias", categoriaService.obtenerCategorias());
        model.addAttribute("productoId", id);
        return "producto/formulario";
    }

    @PostMapping("/{id}")
    public String actualizarProducto(@PathVariable Long id,
                                     @Valid @ModelAttribute("producto") ProductoRequestDTO requestDTO,
                                     BindingResult bindingResult, Model model,
                                     @RequestParam(name = "imagen") MultipartFile imagen) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categorias", categoriaService.obtenerCategorias());
            model.addAttribute("productoId", id);
            return "producto/formulario";
        }
        productoService.actualizarProducto(id, requestDTO, imagen);
        return "redirect:/productos/" + id;
    }

    @PostMapping("/{id}/eliminar")
    public String eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return "redirect:/productos";
    }
}
