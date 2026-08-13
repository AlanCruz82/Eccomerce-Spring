package com.spring.eccomerce.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;

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
        return "redirect:/categorias";
    }

    @ExceptionHandler(UsuarioNotFoundException.class)
    public String handleUsuarioNotFound(UsuarioNotFoundException ex, RedirectAttributes ra, HttpServletRequest request) {
        ra.addFlashAttribute("error", ex.getMessage());
        return destinoUsuario(request);
    }

    @ExceptionHandler(ProductoDuplicadoException.class)
    public String handleProductoDuplicado(ProductoDuplicadoException ex, RedirectAttributes ra) {
        ra.addFlashAttribute("error", ex.getMessage());
        return "redirect:/productos";
    }

    @ExceptionHandler(CategoriaDuplicadaException.class)
    public String handleCategoriaDuplicada(CategoriaDuplicadaException ex, RedirectAttributes ra) {
        ra.addFlashAttribute("error", ex.getMessage());
        return "redirect:/categorias";
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

    @ExceptionHandler(UsuarioDuplicadoException.class)
    public String handleUsuarioDuplicado(UsuarioDuplicadoException ex, RedirectAttributes ra, HttpServletRequest request) {
        ra.addFlashAttribute("error", ex.getMessage());
        return destinoUsuario(request);
    }

    @ExceptionHandler(RolNotFoundException.class)
    public String handleRolNotFound(RolNotFoundException ex, RedirectAttributes ra, HttpServletRequest request) {
        ra.addFlashAttribute("error", ex.getMessage());
        return destinoUsuario(request);
    }

    @ExceptionHandler(ImagenExcedeTamanoException.class)
    public String handleImagenExcedeTamano(ImagenExcedeTamanoException ex, RedirectAttributes ra) {
        ra.addFlashAttribute("error", ex.getMessage());
        return "redirect:/productos";
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxUploadSize(MaxUploadSizeExceededException ex, RedirectAttributes ra) {
        ra.addFlashAttribute("error", "La imagen supera el tamaño máximo permitido");
        return "redirect:/productos";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneral(Exception ex, RedirectAttributes ra) {
        ra.addFlashAttribute("error", "Ocurrió un error inesperado");
        return "redirect:/";
    }

    //Metodo para redirigir los errores de usuario hacia la zona de administracion cuando la peticion proviene de ella
    private String destinoUsuario(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/admin")
                ? "redirect:/admin/usuarios"
                : "redirect:/registro";
    }
}
