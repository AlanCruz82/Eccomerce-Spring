package com.spring.eccomerce.entity;

import com.spring.eccomerce.entity.base.Bitacora;
import com.spring.eccomerce.entity.enums.EstadoPedido;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Entity
@Table(name = "pedidos")
public class Pedido extends Bitacora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoPedido estadoPedido;

    @Column(name = "importe_total", nullable = false)
    private BigDecimal importeTotal;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

}
