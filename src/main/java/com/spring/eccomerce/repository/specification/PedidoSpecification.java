package com.spring.eccomerce.repository.specification;

import com.spring.eccomerce.entity.Pedido;
import com.spring.eccomerce.entity.enums.EstadoPedido;
import org.springframework.data.jpa.domain.Specification;

public class PedidoSpecification {

    //Consulta para obtener los pedidos con el estado enviado como parametro
    public static Specification<Pedido> estadoEquals(EstadoPedido estado) {
        return (root, query, criteriaBuilder) ->
                estado == null ? null : criteriaBuilder.equal(root.get("estadoPedido"), estado);
    }

    //Consulta para obtener los pedidos con el id del usuario enviado como parametro
    public static Specification<Pedido> usuarioIdEquals(Long usuarioId) {
        return (root, query, criteriaBuilder) ->
                usuarioId == null ? null : criteriaBuilder.equal(root.get("usuario").get("id"), usuarioId);
    }
}
