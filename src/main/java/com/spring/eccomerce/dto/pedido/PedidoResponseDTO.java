package com.spring.eccomerce.dto.pedido;

import com.spring.eccomerce.dto.detallesPedido.DetallesPedidoResponseDTO;
import com.spring.eccomerce.dto.usuario.UsuarioResumenDTO;
import com.spring.eccomerce.entity.enums.EstadoPedido;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Setter @Getter
public class PedidoResponseDTO {

    private Long id;
    private EstadoPedido estadoPedido;
    private BigDecimal importeTotal;
    private UsuarioResumenDTO usuario;
    private String direccionEnvio;
    private List<DetallesPedidoResponseDTO> detalles;
    private LocalDateTime fechaActualizacion;
}
