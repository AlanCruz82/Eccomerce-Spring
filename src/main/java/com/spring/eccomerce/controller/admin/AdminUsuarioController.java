package com.spring.eccomerce.controller.admin;

import com.spring.eccomerce.dto.usuario.UsuarioEditRequestDTO;
import com.spring.eccomerce.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/usuarios")
public class AdminUsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public String listarUsuarios(@RequestParam(required = false) String busqueda,
                                 @RequestParam(defaultValue = "0") int pagina,
                                 @RequestParam(defaultValue = "10") int tamano,
                                 Model model) {
        Pageable pageable = PageRequest.of(pagina, tamano);
        model.addAttribute("usuarios", usuarioService.obtenerUsuarios(busqueda, pageable));
        model.addAttribute("busqueda", busqueda);
        return "usuario/lista";
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("usuario", usuarioService.obtenerUsuarioEditar(id));
        model.addAttribute("usuarioId", id);
        return "usuario/formulario";
    }

    @PostMapping("/{id}")
    public String actualizarUsuario(@PathVariable Long id,
                                    @Valid @ModelAttribute("usuario") UsuarioEditRequestDTO dto,
                                    BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("usuarioId", id);
            return "usuario/formulario";
        }
        usuarioService.actualizarUsuario(id, dto);
        return "redirect:/admin/usuarios";
    }

    @PostMapping("/{id}/estado")
    public String cambiarEstado(@PathVariable Long id, @RequestParam boolean activo) {
        usuarioService.cambiarEstadoActivo(id, activo);
        return "redirect:/admin/usuarios";
    }
}