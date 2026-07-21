package com.spring.eccomerce.service.impl;

import com.spring.eccomerce.dto.pedido.PedidoDetalleDTO;
import com.spring.eccomerce.dto.pedido.PedidoResponseDTO;
import com.spring.eccomerce.dto.pedido.PedidoResumenDTO;
import com.spring.eccomerce.entity.Pedido;
import com.spring.eccomerce.entity.Usuario;
import com.spring.eccomerce.entity.enums.EstadoPedido;
import com.spring.eccomerce.exception.PedidoNotFoundException;
import com.spring.eccomerce.exception.UsuarioNotFoundException;
import com.spring.eccomerce.mapper.PedidoMapper;
import com.spring.eccomerce.repository.PedidoRepository;
import com.spring.eccomerce.repository.UsuarioRepository;
import com.spring.eccomerce.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    //Dependencia para realizar las consultas a la base de datos
    private final PedidoRepository pedidoRepository;
    //Dependencia para convetir las entidades del pedido en su formato DTO
    private final PedidoMapper pedidoMapper;
    //Dependencia para validar el id del usuario en su capa de datos
    private final UsuarioRepository usuarioRepository;

    @Override
    public PedidoDetalleDTO obtenerPedidoPorId(Long id) {
        //Validamos si el pedido con el id enviado como parametro existe en la base de datos
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(
                () -> new PedidoNotFoundException(id)
        );

        //Regresamos el pedido encontrado en la base de datos en su formato detalleDTO
        return pedidoMapper.toDetalleDTO(pedido);
    }

    @Override
    public Page<PedidoResumenDTO> obtenerPedidos(Pageable pagina) {
        //Regresamos la(s) pagina(s) de pedidos encontrado en su formato de resumenDTO
        return pedidoRepository.findAll(pagina).map(pedidoMapper::toResumenDTO);

    }

    @Override
    public Page<PedidoResumenDTO> obtenerPedidosPorUsuarioId(Long idUsuario, Pageable pagina) {
        //Validamos si el usuario con el id enviado como parametro, existe en la base de datos
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(
                () -> new UsuarioNotFoundException(idUsuario)
        );

        //Regresamos la pagina de pedidos encontrados del usuario con el id enviado como parametro
        return pedidoRepository.findByUsuarioId(idUsuario, pagina).map(pedidoMapper::toResumenDTO);
    }

    @Override
    public Page<PedidoResumenDTO> obtenerPedidosPorEstado(EstadoPedido estado, Pageable pagina) {
        //Regresamos la pagina de pedidos encontrados por el estado enviado como argumento
        return pedidoRepository.findByEstadoPedido(estado, pagina).map(pedidoMapper::toResumenDTO);
    }

    @Override
    public PedidoResponseDTO actualizarEstado(Long id, EstadoPedido nuevoEstado) {
        //Obtenemos el pedido con el id enviado como argumento
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(
                () -> new PedidoNotFoundException(id)
        );

        //Actualizamos su estado por el enviado como argumento
        pedido.setEstadoPedido(nuevoEstado);

        //Guardamos el pedido con su nuevo estado y regresamos el pedido en su formato responseDTO
        return pedidoMapper.toDTO(pedidoRepository.save(pedido));
    }
}
