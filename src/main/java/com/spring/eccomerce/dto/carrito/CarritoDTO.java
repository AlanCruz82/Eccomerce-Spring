package com.spring.eccomerce.dto.carrito;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Setter @Getter
public class CarritoDTO {

    private List<ItemCarritoDTO> items = new ArrayList<>();
    private final BigDecimal costoEnvio = new BigDecimal("0.0");

    public Integer getCantidadProductos(){
        return items.stream().mapToInt(ItemCarritoDTO::getCantidad).sum();
    }

    public BigDecimal getSubtotal() {
        return items.stream()
                .map(ItemCarritoDTO::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotal(){
        return getSubtotal().add(costoEnvio);
    }

    public void agregarItem(ItemCarritoDTO item){
        items.add(item);
    }

    public void eliminarItem(Long idProducto){
        items.removeIf(item -> item.getIdProducto().equals(idProducto));
    }

    public void vaciar(){
        items.clear();
    }

    public boolean contieneProducto(Long idProducto){
        return items.stream().anyMatch(item -> item.getIdProducto().equals(idProducto));
    }

    public ItemCarritoDTO getItem(Long idProducto){
        return items.stream().filter(item -> item.getIdProducto().equals(idProducto)).findFirst().orElse(null);
    }
}
