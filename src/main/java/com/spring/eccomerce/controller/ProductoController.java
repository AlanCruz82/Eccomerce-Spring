package com.spring.eccomerce.controller;

import com.spring.eccomerce.dto.producto.*;
import com.spring.eccomerce.service.CategoriaService;
import com.spring.eccomerce.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    @GetMapping
    public String listarProductos(@ModelAttribute ProductoFiltroDTO filtroDTO,
                                  @RequestParam(defaultValue = "0") int pagina,
                                  @RequestParam(defaultValue = "10") int tamano,
                                  Model model) {
        Pageable pageable = PageRequest.of(pagina, tamano);
        Page<ProductoResumenDTO> productos = productoService.obtenerProductos(filtroDTO, pageable);
        model.addAttribute("productos", productos);
        model.addAttribute("filtro", filtroDTO);
        model.addAttribute("categorias", categoriaService.obtenerCategorias());
        return "producto/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("producto", new ProductoRequestDTO());
        model.addAttribute("categorias", categoriaService.obtenerCategorias());
        return "producto/formulario";
    }

    @PostMapping
    public String crearProducto(@Valid @ModelAttribute("producto") ProductoRequestDTO requestDTO) {
        ProductoResumenDTO creado = productoService.crearProducto(requestDTO);
        return "redirect:/producto/" + creado.getId();
    }

    @GetMapping("/{id}")
    public String verProducto(@PathVariable Long id, Model model) {
        model.addAttribute("producto", productoService.obtenerProductoPorId(id));
        return "producto/detalle";
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        ProductoDetalleDTO response = productoService.obtenerProductoPorId(id);
        ProductoRequestDTO requestDTO = new ProductoRequestDTO();
        requestDTO.setNombre(response.getNombre());
        requestDTO.setDescripcion(response.getDescripcion());
        requestDTO.setPrecio(response.getPrecio());
        requestDTO.setExistencia(response.getExistencia());
        requestDTO.setUrlImagen(response.getUrlImagen());
        requestDTO.setIdCategoria(response.getCategoria().getId());
        model.addAttribute("producto", requestDTO);
        model.addAttribute("categorias", categoriaService.obtenerCategorias());
        model.addAttribute("productoId", id);
        return "producto/formulario";
    }

    @PostMapping("/{id}")
    public String actualizarProducto(@PathVariable Long id,
                                     @Valid @ModelAttribute("producto") ProductoRequestDTO requestDTO) {
        productoService.actualizarProducto(id, requestDTO);
        return "redirect:/producto/" + id;
    }

    @PostMapping("/{id}/eliminar")
    public String eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return "redirect:/producto";
    }
}
