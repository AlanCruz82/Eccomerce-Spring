package com.spring.eccomerce.mapper;

import com.spring.eccomerce.dto.categoria.CategoriaResumenDTO;
import com.spring.eccomerce.dto.producto.ProductoDetalleDTO;
import com.spring.eccomerce.dto.producto.ProductoRequestDTO;
import com.spring.eccomerce.dto.producto.ProductoResponseDTO;
import com.spring.eccomerce.dto.producto.ProductoResumenDTO;
import com.spring.eccomerce.entity.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    private final CategoriaMapper categoriaMapper;

    public ProductoMapper(CategoriaMapper categoriaMapper) {
        this.categoriaMapper = categoriaMapper;
    }

    public ProductoResponseDTO toDTO(Producto producto) {

        //COnstruimos el dto con sus campos definidos agregando el resumen de la categoria generada anteriormente
        return ProductoResponseDTO.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .precio(producto.getPrecio())
                .existencia(producto.getExistencia())
                .urlImagen(producto.getUrlImagen())
                .fechaCreacion(producto.getFechaCreacion())
                .fechaActualizacion(producto.getFechaActualizacion())
                .categoria(categoriaMapper.toResumenDTO(producto.getCategoria()))
                .build();
    }
    public Producto toEntity(ProductoRequestDTO dto) {

        //Generamos la entidad de producto con los campos enviados por el dto
        //AUN SIN LA CATEGORIA ASIGNADA, YA QUE ESO SE HACE EN EL SERVICIO
        return Producto.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .precio(dto.getPrecio())
                .existencia(dto.getExistencia())
                .urlImagen(dto.getUrlImagen())
                .build();
    }

    public ProductoResumenDTO toResumenDTO(Producto producto){

        //Generamos el dto que resume la entidad del producto con los campos necesarios indicados en el dtoResumen
        return ProductoResumenDTO.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .precio(producto.getPrecio())
                .urlImagen(producto.getUrlImagen())
                .existencia(producto.getExistencia())
                .categoria(categoriaMapper.toResumenDTO(producto.getCategoria()))
                .build();
    }

    public ProductoDetalleDTO toDetalleDTO(Producto producto){
        //Generamos el detalle del producto referenciando los campos de la entidad producto con los del detalleDTO
        return ProductoDetalleDTO.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .precio(producto.getPrecio())
                .urlImagen(producto.getUrlImagen())
                .existencia(producto.getExistencia())
                .categoria(categoriaMapper.toResumenDTO(producto.getCategoria()))
                .build();
    }

    //Caso de uso: al editar un producto que ya existe en la base de datos
    public ProductoRequestDTO toRequestDTO(ProductoDetalleDTO detalleDTO){
        //Generamos el productoRequest, que va a contener los datos del producto que ya esta almacenado en la base de datos
        ProductoRequestDTO requestDTO = new  ProductoRequestDTO();

        //Establecemos la informacion del productoRequest en base a la informacion del producto almacenado
        requestDTO.setNombre(detalleDTO.getNombre());
        requestDTO.setDescripcion(detalleDTO.getDescripcion());
        requestDTO.setPrecio(detalleDTO.getPrecio());
        requestDTO.setExistencia(detalleDTO.getExistencia());
        requestDTO.setUrlImagen(detalleDTO.getUrlImagen());
        requestDTO.setIdCategoria(detalleDTO.getCategoria().getId());

        //Regresamos el productoRequestDTO generado con los datos del producto
        return requestDTO;
    }
}
