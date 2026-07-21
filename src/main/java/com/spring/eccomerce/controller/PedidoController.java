package com.spring.eccomerce.controller;

import com.spring.eccomerce.entity.enums.EstadoPedido;
import com.spring.eccomerce.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    //Dependencia para aplicar la logica de negocios y hacer las consultas a la capa de datos
    private final PedidoService pedidoService;

    @GetMapping("/{id}")
    public String mostrarDetalle(@PathVariable Long id, Model model){
        model.addAttribute("pedido", pedidoService.obtenerPedidoPorId(id));
        return "pedido/detalle";
    }

    @GetMapping("/{id}/confirmacion")
    public String confirmarPedido(@PathVariable Long id, Model model){
        model.addAttribute("pedido", pedidoService.obtenerPedidoPorId(id));
        return "pedido/confirmacion";
    }

    @GetMapping
    public String mostrarPedidos(@RequestParam(defaultValue = "0") int pagina,
                                 @RequestParam(defaultValue = "5") int tamanio,
                                 Model model){
        //Generamos la pagina de los parametros enviados en la url
        Pageable page =  PageRequest.of(pagina, tamanio);
        model.addAttribute("pedidos", pedidoService.obtenerPedidos(page));
        return "pedido/lista";
    }

    @GetMapping("/{id}/usuario")
    public String mostrarPedidosPorUsuario(@PathVariable Long id,
                                           @RequestParam(defaultValue = "0") int pagina,
                                           @RequestParam(defaultValue = "5") int tamanio,
                                           Model model){
        //Generamos la pagina de los parametros enviados en la url
        Pageable page =  PageRequest.of(pagina, tamanio);
        model.addAttribute("pedidos", pedidoService.obtenerPedidosPorUsuarioId(id, page));
        return "pedido/lista";
    }

    @GetMapping("/{estado}/estado")
    public String mostrarPedidosPorEstado(@PathVariable EstadoPedido estado,
                                           @RequestParam(defaultValue = "0") int pagina,
                                           @RequestParam(defaultValue = "5") int tamanio,
                                           Model model){
        //Generamos la pagina de los parametros enviados en la url
        Pageable page =  PageRequest.of(pagina, tamanio);
        model.addAttribute("pedidos", pedidoService.obtenerPedidosPorEstado(estado, page));
        return "pedido/lista";
    }




}
