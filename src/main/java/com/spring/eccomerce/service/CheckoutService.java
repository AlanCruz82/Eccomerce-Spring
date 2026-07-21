package com.spring.eccomerce.service;

import com.spring.eccomerce.dto.checkout.CheckoutDTO;
import com.spring.eccomerce.dto.pedido.PedidoResponseDTO;

public interface CheckoutService {

    public CheckoutDTO obtenerFormulario();
    public PedidoResponseDTO confirmarCompra(CheckoutDTO checkoutDTO);
}
