package com.spring.eccomerce.mapper;

import com.spring.eccomerce.dto.pedido.PedidoResponseDTO;
import com.spring.eccomerce.entity.Pedido;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper {

    private final UsuarioMapper usuarioMapper;

    public PedidoMapper(UsuarioMapper usuarioMapper) {
        this.usuarioMapper = usuarioMapper;
    }

    public PedidoResponseDTO toDTO(Pedido pedido){

        //Generamos el dto del pedido asociando cada campo del dto al de la entidad pedido
        //AUN SIN AGREGAR LOS DETALLES DEL PEDIDO (PRODUCTOS), ya que eso se hace en el servicio al consultar
        //los detalles relacionados al pedido
        return PedidoResponseDTO.builder()
                .id(pedido.getId())
                .estadoPedido(pedido.getEstadoPedido())
                .importeTotal(pedido.getImporteTotal())
                //.usuario(usuarioMapper.toResumenDTO(pedido.getUsuario()))
                .direccionEnvio(pedido.getDireccionEnvio())
                .fechaActualizacion(pedido.getFechaActualizacion())
                .build();
    }

    //NO existe un mapper para convertir a un dto a una entidad de pedido, ya que el pedido se va a generar manualmente
    //en el servicio, al no poder contar con los elementos suficientes para poder generarlo aqui
}
