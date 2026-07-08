package com.spring.eccomerce.mapper;

import com.spring.eccomerce.dto.detallesPedido.DetallesPedidoResponseDTO;
import com.spring.eccomerce.entity.DetallePedido;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DetallePedidoMapper {

    private final ProductoMapper productoMapper;

    public DetallePedidoMapper(ProductoMapper productoMapper) {
        this.productoMapper = productoMapper;
    }

    public DetallesPedidoResponseDTO toDTO(DetallePedido dp){
        return DetallesPedidoResponseDTO.builder()
                .id(dp.getIdDetallePedido())
                .producto(productoMapper.toResumenDTO(dp.getProducto()))
                .cantidad(dp.getCantidad())
                .precioUnitario(dp.getPrecioUnitario())
                .subtotal(dp.getPrecioUnitario().multiply(BigDecimal.valueOf(dp.getCantidad())))
                .build();
    }
}
