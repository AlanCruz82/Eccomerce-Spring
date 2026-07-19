package com.spring.eccomerce.controller;

import com.spring.eccomerce.dto.checkout.CheckoutDTO;
import com.spring.eccomerce.service.CarritoService;
import com.spring.eccomerce.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    //Dependencia para obtener el carrito de la sesion y sus items
    private final CarritoService carritoService;
    //Depedencia para procesar la logica al realizar la compra del carrito
    private final CheckoutService checkoutService;
    @GetMapping
    public String mostrarCheckout(Model model) {

        //Si el carro de la sesion esta vacio, redirigimos a la vista del carrito
        if (carritoService.obtenerCarrito().getItems().isEmpty()) {
            return "redirect:/carrito";
        }

        //Agregamos al modelo el objeto checkout que se va a llenar con la informacion del formulario y el carrito de la sesion
        model.addAttribute("checkout", new CheckoutDTO());
        model.addAttribute("carrito", carritoService.obtenerCarrito());

        return "checkout/index";
    }

    @PostMapping
    public String procesarCompra(CheckoutDTO checkoutDTO){
        checkoutService.confirmarCompra(checkoutDTO);
        return "redirect:/productos";
    }
}
