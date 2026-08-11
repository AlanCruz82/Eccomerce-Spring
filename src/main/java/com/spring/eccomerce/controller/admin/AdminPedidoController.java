package com.spring.eccomerce.controller.admin;

import com.spring.eccomerce.dto.usuario.UsuarioResumenDTO;
import com.spring.eccomerce.entity.enums.EstadoPedido;
import com.spring.eccomerce.service.PedidoService;
import com.spring.eccomerce.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/pedidos")
@RequiredArgsConstructor
public class AdminPedidoController {

    private final PedidoService pedidoService;
    private final UsuarioService usuarioService;

    @GetMapping
    public String listarPedidos(@RequestParam(defaultValue = "0") int pagina,
                                @RequestParam(defaultValue = "5") int tamanio,
                                @RequestParam(required = false) EstadoPedido estado,
                                @RequestParam(required = false) Long usuarioId,
                                Model model) {

        //Generamos la pagina en base a los parametros enviado en la url
        Pageable page = PageRequest.of(pagina, tamanio);
        //Obtenemos la lista de usuarios que vamos a utilizar en el dropdown
        List<UsuarioResumenDTO> usuarios = usuarioService.obtenerTodos();

        model.addAttribute("pedidos", pedidoService.obtenerPedidosFiltrados(estado, usuarioId, page));
        model.addAttribute("estados", EstadoPedido.values());
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("filtroEstado", estado);
        model.addAttribute("filtroUsuarioId", usuarioId);
        return "pedido/lista";
    }

    @GetMapping("/{id}")
    public String mostrarDetalle(@PathVariable Long id, Model model) {
        model.addAttribute("pedido", pedidoService.obtenerPedidoPorId(id));
        return "pedido/detalle";
    }

    @PostMapping("/{id}/estado")
    public String actualizarEstado(@PathVariable Long id,
                                   @RequestParam EstadoPedido estado,
                                   RedirectAttributes ra) {
        pedidoService.actualizarEstado(id, estado);
        ra.addFlashAttribute("actualizado", "Estado del pedido actualizado a " + estado + " correctamente");
        return "redirect:/admin/pedidos/" + id;
    }
}
