package com.spring.eccomerce.dto.pedido;

import com.spring.eccomerce.entity.enums.EstadoPedido;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Setter @Getter
public class PedidoResumenDTO {

    private Long id;
    private EstadoPedido estado;
    private BigDecimal total;
    private LocalDateTime fechaActualizacion;
}
