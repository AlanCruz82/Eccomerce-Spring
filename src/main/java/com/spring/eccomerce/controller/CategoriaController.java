package com.spring.eccomerce.controller;

import com.spring.eccomerce.dto.categoria.CategoriaRequestDTO;
import com.spring.eccomerce.dto.categoria.CategoriaResponseDTO;
import com.spring.eccomerce.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping("/nueva")
    public String mostrarFormularioNuevaCategoria(Model model) {
        model.addAttribute("categoria", new CategoriaRequestDTO());
        return "categoria/formulario";
    }

    @PostMapping
    public String crearCategoria(@Valid @ModelAttribute("categoria") CategoriaRequestDTO requestDTO) {
        categoriaService.crearCategoria(requestDTO);
        return "redirect:/categorias";
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        CategoriaResponseDTO categoria = categoriaService.obtenerCategoriaPorId(id);
        CategoriaRequestDTO dto = new CategoriaRequestDTO();
        dto.setNombre(categoria.getNombre());
        model.addAttribute("categoria", dto);
        model.addAttribute("categoriaId", id);
        return "categoria/formulario";
    }

    @PostMapping("/{id}")
    public String actualizarCategoria(@PathVariable Long id,
                                      @Valid @ModelAttribute("categoria") CategoriaRequestDTO requestDTO) {
        categoriaService.actualizarCategoria(id, requestDTO);
        return "redirect:/categorias";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminarCategoria(@PathVariable Long id) {
        categoriaService.eliminarCategoria(id);
        return "redirect:/categorias";
    }

    @GetMapping
    public String listarCategorias(Model model) {
        model.addAttribute("categorias", categoriaService.obtenerCategorias());
        return "categoria/lista";
    }
}
