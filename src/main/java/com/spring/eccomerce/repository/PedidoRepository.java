package com.spring.eccomerce.repository;

import com.spring.eccomerce.entity.enums.EstadoPedido;
import com.spring.eccomerce.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    Optional<Pedido> findByEstadoPedido(EstadoPedido estado);
    Optional<Pedido> findByUsuarioId(Long id);
    boolean existsByUsuarioId(Long id);
}
