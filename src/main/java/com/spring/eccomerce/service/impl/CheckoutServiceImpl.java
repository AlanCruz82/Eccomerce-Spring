package com.spring.eccomerce.service.impl;

import com.spring.eccomerce.dto.carrito.CarritoDTO;
import com.spring.eccomerce.dto.carrito.ItemCarritoDTO;
import com.spring.eccomerce.dto.checkout.CheckoutDTO;
import com.spring.eccomerce.dto.pedido.PedidoResponseDTO;
import com.spring.eccomerce.entity.DetallePedido;
import com.spring.eccomerce.entity.Pedido;
import com.spring.eccomerce.entity.Producto;
import com.spring.eccomerce.entity.enums.EstadoPedido;
import com.spring.eccomerce.mapper.PedidoMapper;
import com.spring.eccomerce.repository.PedidoRepository;
import com.spring.eccomerce.repository.ProductoRepository;
import com.spring.eccomerce.service.CarritoService;
import com.spring.eccomerce.service.CheckoutService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CheckoutServiceImpl implements CheckoutService {

    //Dependencia para obtener el carrito de la sesion y sus items
    private final CarritoService carritoService;
    //Dependencia para actualizar las existencias de los productos del carrito
    private final ProductoRepository productoRepository;
    //Dependencia para generar el pedido en base a los productos del carrito
    private final PedidoRepository pedidoRepository;
    //Dependencia para regresar el pedido generado en su formato responseDTO
    private final PedidoMapper pedidoMapper;

    @Override
    public CheckoutDTO obtenerFormulario() {
        return null;
    }

    @Override
    @SneakyThrows
    //Usamos una transaccion para asegurar que todas las operaciones en la base de datos se hagan juntas o si no ninguna se haga
    @Transactional
    public PedidoResponseDTO confirmarCompra(CheckoutDTO checkoutDTO) {
        //Obtenemos el carrito de la sesion con sus items
        CarritoDTO carrito = carritoService.obtenerCarrito();

        //Validamos si el carrito de la sesion contiene elementos
        if(carrito.getItems().isEmpty()){
            //Si esta vacio, avisamos para no ejecutar el resto de la logica
            throw new Exception("El carrito de la sesion no contiene ningun item");
        }

        //Obtenemos los productos del carrito
        Map<Long,Producto> productos = obtenerProductos(carrito);

        //Validamos si la existencia de cada producto/item del carrito sigue existiendo en el momento que
        //se quiere hacer la compra
        for (ItemCarritoDTO item : carrito.getItems()){
            //Si las existencias del item del carrito son mayores a las existencias del producto
            if(item.getCantidad() > productos.get(item.getIdProducto()).getExistencia()){
                //Avisamos que las existencias del item se agotaron y por ello no se puede realizar la compra
                throw new Exception("El producto con id " + item.getIdProducto() + " ya no tiene existencias suficientes");
            }
        }

        //Generamos el pedido que vamos a almacenar, en base a los datos de envio recibidos en el dto, el monto total del
        //carrito de la sesion y el estado y fecha generados manualmente
        Pedido pedido = new Pedido();
        pedido.setUsuario(null); //TEMPORAL: Con spring security, podemos establecerlo con el SecurityContextHolder
        pedido.setEstadoPedido(EstadoPedido.PENDIENTE);
        pedido.setImporteTotal(carrito.getTotal());
        pedido.setDireccionEnvio(construirDireccionEnvio(checkoutDTO));

        //Generamos una nueva lista que va a almacenar los detalles del pedido generado
        List<DetallePedido> detalles = new ArrayList<>();

        //Recorremos cada item del carrito para contruir su detalle que se va a relacionar con el pedido generado
        for (ItemCarritoDTO item : carrito.getItems()){
            //Generamos la entidad del detalle del pedido con los campos del item del carrito
            DetallePedido detalle = new DetallePedido();
            detalle.setProducto(productos.get(item.getIdProducto()));
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(item.getPrecio());
            detalle.setPedido(pedido);

            //Almacenamos el detalle generado en la lista de detalles
            detalles.add(detalle);

            //Actualizamos las existencias del producto
            productos.get(item.getIdProducto()).setExistencia(productos.get(item.getIdProducto()).getExistencia() - item.getCantidad());
        }

        //Actualizamos todos los productos en la base de datos
        productoRepository.saveAll(productos.values());

        //Guardamos los detalles del pedido en el pedido generado
        pedido.setDetalles(detalles);

        //Almacenamos el pedido en la base de datos y convertimos la entidad guardada en su formato responseDTO
        PedidoResponseDTO pedidoDTO = pedidoMapper.toDTO(pedidoRepository.save(pedido));

        //Vaciamos el carrito de la sesion
        carrito.vaciar();

        //Regresamos el pedido generado en su formato responseDTO
        return pedidoDTO;
    }

    //Metodo para generar la direccion de envio en base a las entradas (direccionEnvio, ciudad, codigoPostal y referencia
    //del formulario/checkoutDTO)
    private String construirDireccionEnvio(CheckoutDTO dto) {
        return dto.getDireccionEnvio() + ", " +
                dto.getCiudad() + ", CP " +
                dto.getCodigoPostal() +
                (dto.getReferencia() != null && !dto.getReferencia().isBlank()
                        ? ". Referencia: " + dto.getReferencia()
                        : "");
    }

    //Metodo para optimizar las consultas al repositorio del producto
    public Map<Long,Producto> obtenerProductos(CarritoDTO carrito){
        //Obtenemos los ids de los productos del carrito y los convertimos en una lista para hacer la consulta al repositorio
        List<Long> idsProductos = carrito.getItems().stream().map(ItemCarritoDTO::getIdProducto).toList();

        //Obtenemos los productos presentes en el carrito
        List<Producto> productosCarrito = productoRepository.findAllById(idsProductos);

        //Generamos el map de los productos presentes en el carrito con clave-valor (id-producto)
        return productosCarrito.stream().collect(Collectors.toMap(
                Producto::getId, //Clave-id
                Function.identity() //Valor-producto
        ));
    }
}
