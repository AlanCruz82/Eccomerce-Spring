package com.spring.eccomerce.repository;

import com.spring.eccomerce.entity.enums.EstadoPedido;
import com.spring.eccomerce.entity.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>, JpaSpecificationExecutor<Pedido> {

    Page<Pedido> findByUsuarioId(Long id, Pageable pagina);
}
