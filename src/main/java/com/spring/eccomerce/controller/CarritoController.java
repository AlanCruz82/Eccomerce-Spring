package com.spring.eccomerce.controller;

import com.spring.eccomerce.service.CarritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/carrito")
@RequiredArgsConstructor
public class CarritoController {

    private final CarritoService carritoService;

    @GetMapping
    public String verCarrito(Model model) {
        model.addAttribute("carrito", carritoService.obtenerCarrito());
        return "carrito/index";
    }

    @PostMapping("/agregar/{id}")
    public String agregarProducto(@PathVariable Long id) {
        carritoService.agregarProducto(id);
        return "redirect:/carrito";
    }

    @PostMapping("/aumentar/{id}")
    public String aumentarCantidad(@PathVariable Long id) {
        carritoService.aumentarCantidad(id);
        return "redirect:/carrito";
    }

    @PostMapping("/disminuir/{id}")
    public String disminuirCantidad(@PathVariable Long id) {
        carritoService.disminuirCantidad(id);
        return "redirect:/carrito";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        carritoService.eliminarProducto(id);
        return "redirect:/carrito";
    }

    @PostMapping("/vaciar")
    public String vaciarCarrito() {
        carritoService.vaciarCarrito();
        return "redirect:/carrito";
    }
}
