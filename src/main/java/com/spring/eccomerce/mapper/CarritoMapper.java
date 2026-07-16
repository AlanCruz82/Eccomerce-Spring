package com.spring.eccomerce.mapper;

import com.spring.eccomerce.dto.carrito.ItemCarritoDTO;
import com.spring.eccomerce.entity.Producto;
import org.springframework.stereotype.Component;

@Component
public class CarritoMapper {

    public ItemCarritoDTO toItemDTO(Producto producto){
        //Generamos el itemDTO con los valores de los campos del producto
        //EXCEPTO la CANTIDAD, ya que eso es responsabilida de la capa de servicio
        ItemCarritoDTO item = new ItemCarritoDTO();
        item.setIdProducto(producto.getId());
        item.setNombre(producto.getNombre());
        item.setPrecio(producto.getPrecio());
        item.setUrlImagen(producto.getUrlImagen());

        return item;
    }
}
