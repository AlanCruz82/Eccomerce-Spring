package com.spring.eccomerce.controller.admin;

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
@RequestMapping("/admin/categorias")
public class AdminCategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping("/nueva")
    public String mostrarFormularioNuevaCategoria(Model model) {
        model.addAttribute("categoria", new CategoriaRequestDTO());
        return "categoria/formulario";
    }

    @PostMapping
    public String crearCategoria(@Valid @ModelAttribute("categoria") CategoriaRequestDTO requestDTO) {
        categoriaService.crearCategoria(requestDTO);
        return "redirect:/admin/categorias";
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("categoria", categoriaService.obtenerCategoriaEditar(id));
        model.addAttribute("categoriaId", id);
        return "categoria/formulario";
    }

    @PostMapping("/{id}")
    public String actualizarCategoria(@PathVariable Long id,
                                      @Valid @ModelAttribute("categoria") CategoriaRequestDTO requestDTO) {
        categoriaService.actualizarCategoria(id, requestDTO);
        return "redirect:/admin/categorias";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminarCategoria(@PathVariable Long id) {
        categoriaService.eliminarCategoria(id);
        return "redirect:/admin/categorias";
    }

    @GetMapping
    public String listarCategorias(Model model) {
        model.addAttribute("categorias", categoriaService.obtenerCategorias());
        return "categoria/lista";
    }
}
