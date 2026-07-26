package com.spring.eccomerce.controller.admin;

import com.spring.eccomerce.entity.enums.EstadoPedido;
import com.spring.eccomerce.service.PedidoService;
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

@Controller
@RequestMapping("/admin/pedidos")
@RequiredArgsConstructor
public class AdminPedidoController {

    private final PedidoService pedidoService;

    @GetMapping
    public String listarPedidos(@RequestParam(defaultValue = "0") int pagina,
                                @RequestParam(defaultValue = "5") int tamanio,
                                Model model) {
        Pageable page = PageRequest.of(pagina, tamanio);
        model.addAttribute("pedidos", pedidoService.obtenerPedidos(page));
        return "pedido/lista";
    }

    @GetMapping("/{id}")
    public String mostrarDetalle(@PathVariable Long id, Model model) {
        model.addAttribute("pedido", pedidoService.obtenerPedidoPorId(id));
        return "pedido/detalle";
    }

    @PostMapping("/{id}/estado")
    public String actualizarEstado(@PathVariable Long id,
                                   @RequestParam EstadoPedido estado) {
        pedidoService.actualizarEstado(id, estado);
        return "redirect:/admin/pedidos/" + id;
    }
}
