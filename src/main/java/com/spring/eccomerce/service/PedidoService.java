package com.spring.eccomerce.service;

import com.spring.eccomerce.dto.pedido.PedidoDetalleDTO;
import com.spring.eccomerce.dto.pedido.PedidoResponseDTO;
import com.spring.eccomerce.dto.pedido.PedidoResumenDTO;
import com.spring.eccomerce.entity.enums.EstadoPedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PedidoService {

    PedidoDetalleDTO obtenerPedidoPorId(Long id);
    Page<PedidoResumenDTO> obtenerPedidos(Pageable pagina);
    Page<PedidoResumenDTO> obtenerPedidosPorUsuarioId(Long idUsuario, Pageable pagina);
    Page<PedidoResumenDTO> obtenerPedidosPorEstado(EstadoPedido estado, Pageable pagina);
    PedidoResponseDTO actualizarEstado(Long id, EstadoPedido nuevoEstado);
}
