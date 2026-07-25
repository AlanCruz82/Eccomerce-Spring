package com.spring.eccomerce.service;

import com.spring.eccomerce.dto.checkout.CheckoutDTO;
import com.spring.eccomerce.dto.pedido.PedidoResponseDTO;

public interface CheckoutService {

    CheckoutDTO obtenerFormulario();
    PedidoResponseDTO confirmarCompra(CheckoutDTO checkoutDTO);
}
