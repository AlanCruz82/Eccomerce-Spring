package com.spring.eccomerce.controller;

import com.spring.eccomerce.dto.producto.ProductoFiltroDTO;
import com.spring.eccomerce.dto.producto.ProductoResumenDTO;
import com.spring.eccomerce.service.CategoriaService;
import com.spring.eccomerce.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/{id}")
    public String verProducto(@PathVariable Long id, Model model) {
        model.addAttribute("producto", productoService.obtenerProductoPorId(id));
        return "producto/detalle";
    }
}
