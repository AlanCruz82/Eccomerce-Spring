package com.spring.eccomerce.service.impl;

import com.spring.eccomerce.dto.carrito.CarritoDTO;
import com.spring.eccomerce.dto.carrito.ItemCarritoDTO;
import com.spring.eccomerce.entity.Producto;
import com.spring.eccomerce.mapper.CarritoMapper;
import com.spring.eccomerce.repository.ProductoRepository;
import com.spring.eccomerce.service.CarritoService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CarritoServiceImpl implements CarritoService {

    //Dependencia para manejar los atributos de la sesion
    private final HttpSession session;
    //Dependencia del repositorio del producto para obtener los productos del carrito
    private final ProductoRepository productoRepository;
    //Dependencia para convertir las entidades de producto a su formato itemDTO
    private final CarritoMapper carritoMapper;

    private CarritoDTO obtenerCarritoSesion() {
        //Obtenemos el carrito ligado a la sesion
        CarritoDTO carrito = (CarritoDTO) session.getAttribute("carrito");

        //Si el carrito todavia no existe generamos uno nuevo para garantizar que siempre exista
        if(carrito == null){
            //Generamos la instancia del carrito
            carrito = new CarritoDTO();
            //La agreamos como atributo de la sesion
            session.setAttribute("carrito", carrito);
        }

        return carrito;
    }

    @Override
    public CarritoDTO obtenerCarrito() {
        return obtenerCarritoSesion();
    }

    @Override
    public void vaciarCarrito() {
        obtenerCarritoSesion().vaciar();
    }

    @Override
    @SneakyThrows
    public void agregarProducto(Long idProducto) {
        //Validamos si el producto que se quiere agregar existe en la base de datos
        Producto producto = productoRepository.findById(idProducto).orElseThrow(
                () -> new RuntimeException("El producto con id " +  idProducto + " no existe")
        );

        //Si el carrito ya cuenta con el producto que se quiere agregar
        if(obtenerCarritoSesion().contieneProducto(idProducto)){
            //Obtenemos el item del carrito
            ItemCarritoDTO item = obtenerCarritoSesion().getItem(idProducto);

            //Si el item puede aumentar su cantidad dentro del carro (la cantidad deseada es menor a las existencias disponibles)
            if(item.puedeAumentarCantidad()){
                //Aumentamos la cantidad del producto que se quiere agregar en uno
                obtenerCarritoSesion().getItem(idProducto).setCantidad(obtenerCarritoSesion().getItem(idProducto).getCantidad() + 1);
            }else {
                //Si ya no se puede aumentar la cantidad
                //Avisamos que ya no se pueden agregar mas productos del producto con el id pasado como parametro
                throw new Exception("Las existencias para el producto con id " + idProducto + " se han agotado");

            }
        }else{
            //Si no, lo agregamos al carrito

            //Convertimos el producto obtenido de la base de datos a su formato de itemDTO
            ItemCarritoDTO item = carritoMapper.toItemDTO(producto);
            //Establecemos la cantidad del item en el carrito en uno, por ser la primera vez que se coloca dentro del carrito
            item.setCantidad(1);

            //Agregamos el item del producto al carrito de la sesion
            obtenerCarritoSesion().agregarItem(item);
        }
    }

    @Override
    public void eliminarProducto(Long idProducto) {
        obtenerCarritoSesion().eliminarItem(idProducto);
    }

    @Override
    @SneakyThrows
    public void aumentarCantidad(Long idProducto) {
        //Obtenemos el item del carrito
        ItemCarritoDTO item = obtenerCarritoSesion().getItem(idProducto);

        //Si su valor es diferente de nulo
        if(item != null){
            //Y si aun quedan existencias del producto
            if(item.puedeAumentarCantidad()){
                //Aumentamos en uno la cantidad del producto dentro del carrito
                item.setCantidad(item.getCantidad() + 1);
            }else{
                //Si no
                //Avisamos que ya no se pueden agregar mas productos del producto con el id pasado como parametro
                throw new Exception("Las existencias para el producto con id " + idProducto + " se han agotado");
            }
        }
    }

    @Override
    public void disminuirCantidad(Long idProducto) {
        CarritoDTO carrito = obtenerCarritoSesion();

        //btenemos el item del carrito
        ItemCarritoDTO item = carrito.getItem(idProducto);

        //Si el valor del item es nulo retornamos
        if(item == null){
            return;
        }

        //Si la cantidad del item que se quiere disminuir es mayor a uno
        if(item.getCantidad() > 1){
            //Disminuimos la cantidad del producto en uno
            item.setCantidad(item.getCantidad() - 1);
        }else{
            //Si no, eliminamos el producto del carrito
            carrito.eliminarItem(idProducto);
        }
    }

    @Override
    public BigDecimal obtenerTotal() {
        return obtenerCarritoSesion().getTotal();
    }
}
