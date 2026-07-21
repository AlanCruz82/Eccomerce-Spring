package com.spring.eccomerce.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductoNotFoundException.class)
    public String handleProductoNotFound(ProductoNotFoundException ex, RedirectAttributes ra) {
        ra.addFlashAttribute("error", ex.getMessage());
        return "redirect:/productos";
    }

    @ExceptionHandler(PedidoNotFoundException.class)
    public String handlePedidoNotFound(PedidoNotFoundException ex, RedirectAttributes ra) {
        ra.addFlashAttribute("error", ex.getMessage());
        return "redirect:/pedidos";
    }

    @ExceptionHandler(CategoriaNotFoundException.class)
    public String handleCategoriaNotFound(CategoriaNotFoundException ex, RedirectAttributes ra) {
        ra.addFlashAttribute("error", ex.getMessage());
        return "redirect:/productos";
    }

    @ExceptionHandler(UsuarioNotFoundException.class)
    public String handleUsuarioNotFound(UsuarioNotFoundException ex, RedirectAttributes ra) {
        ra.addFlashAttribute("error", ex.getMessage());
        return "redirect:/";
    }

    @ExceptionHandler(ProductoDuplicadoException.class)
    public String handleProductoDuplicado(ProductoDuplicadoException ex, RedirectAttributes ra) {
        ra.addFlashAttribute("error", ex.getMessage());
        return "redirect:/productos";
    }

    @ExceptionHandler(CategoriaDuplicadaException.class)
    public String handleCategoriaDuplicada(CategoriaDuplicadaException ex, RedirectAttributes ra) {
        ra.addFlashAttribute("error", ex.getMessage());
        return "redirect:/productos";
    }

    @ExceptionHandler(StockInsuficienteException.class)
    public String handleStockInsuficiente(StockInsuficienteException ex, RedirectAttributes ra) {
        ra.addFlashAttribute("error", ex.getMessage());
        return "redirect:/carrito";
    }

    @ExceptionHandler(CarritoVacioException.class)
    public String handleCarritoVacio(CarritoVacioException ex, RedirectAttributes ra) {
        ra.addFlashAttribute("error", ex.getMessage());
        return "redirect:/carrito";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneral(Exception ex, RedirectAttributes ra) {
        ra.addFlashAttribute("error", "Ocurrió un error inesperado");
        return "redirect:/";
    }
}
