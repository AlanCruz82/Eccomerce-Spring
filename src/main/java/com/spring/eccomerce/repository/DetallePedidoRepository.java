package com.spring.eccomerce.repository;

import com.spring.eccomerce.entity.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {

    List<DetallePedido> findByPedidoId(Long id);
    List<DetallePedido> findByProductoId(Long id);
}
