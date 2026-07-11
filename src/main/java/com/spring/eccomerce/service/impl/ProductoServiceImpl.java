package com.spring.eccomerce.service.impl;

import com.spring.eccomerce.dto.producto.ProductoFiltroDTO;
import com.spring.eccomerce.dto.producto.ProductoRequestDTO;
import com.spring.eccomerce.dto.producto.ProductoResponseDTO;
import com.spring.eccomerce.dto.producto.ProductoResumenDTO;
import com.spring.eccomerce.entity.Categoria;
import com.spring.eccomerce.entity.Producto;
import com.spring.eccomerce.mapper.ProductoMapper;
import com.spring.eccomerce.repository.CategoriaRepository;
import com.spring.eccomerce.repository.ProductoRepository;
import com.spring.eccomerce.repository.specification.ProductoSpecification;
import com.spring.eccomerce.service.ProductoService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

//Inyectamos las depedencias por constructor
@RequiredArgsConstructor
@Service
public class ProductoServiceImpl implements ProductoService {

    //Dependencia para adminsitrar la capa de datos de los productos
    private final ProductoRepository productoRepository;
    //Dependencia para convertir los dtos-entidades y viceversa
    private final ProductoMapper productoMapper;
    //Dependencia para obtener y consultar la categoria del producto
    private final CategoriaRepository categoriaRepository;

    @Override
    public Page<ProductoResumenDTO> obtenerProductos(ProductoFiltroDTO filtroDTO, Pageable pagina) {
        //Generemos la consulta dinmica con las especificaciones enviadas como parametro dentro del dto
        Specification<Producto> especificaciones = Specification.where(ProductoSpecification.categoriaIdEquals(filtroDTO.getCategoriaId()))
                .and(ProductoSpecification.nombreLike(filtroDTO.getNombre()))
                .and(ProductoSpecification.precioGreatherOrEquall(filtroDTO.getPrecioMaximo()))
                .and(ProductoSpecification.precioLessThanOrEqual(filtroDTO.getPrecioMinimo()));

        //Obtenemos la pagina de productos con las especificaciones indicadas y convertimos los productos de la pagina
        //en resumenes de productos

        System.out.println("Elementos obtenido de la especificacion : " + productoRepository.findAll(especificaciones, pagina).getTotalElements());
        return productoRepository.findAll(especificaciones, pagina).map(productoMapper::toResumenDTO);
    }

    @Override
    public ProductoResponseDTO obtenerProductoPorId(Long id) {
        //Verificamos si el producto con el id enviado como argumento existe en la base de datos
        Producto producto = productoRepository.findById(id).orElseThrow(
                () -> new RuntimeException("El producto con el id " + id + " no existe")
        );

        //Si existe, regresamos el producto del id como responseDTO
        return productoMapper.toDTO(producto);
    }

    @Override
    @SneakyThrows
    public ProductoResumenDTO crearProducto(ProductoRequestDTO producto) {
        //Verificamos si ya existe un producto en la base de datos con el nombre del producto que se quiere agregar
        if (productoRepository.existsByNombreIgnoreCase(producto.getNombre())) {
            throw new Exception("El producto con el nombre " + producto.getNombre() + " ya existe");
        }

        //Convertimos el dto enviado como argumento a una entidad de producto
        Producto productoNuevo = productoMapper.toEntity(producto);

        //Obtenemos la categoria enviada como campo de dto que pertenece al producto
        Categoria categoria = categoriaRepository.findById(producto.getIdCategoria()).orElseThrow(
                () -> new Exception("La categoria con id " + producto.getIdCategoria() + " asociada al producto, no existe")
        );

        //Le asignamos la categoria a la entidad producto que acabamos de generar
        productoNuevo.setCategoria(categoria);

        //Guardamos en la base de datos el producto y regresamos el producto creado como resumenDTO
        return productoMapper.toResumenDTO(productoRepository.save(productoNuevo));
    }

    @Override
    public ProductoResumenDTO actualizarProducto(Long id, ProductoRequestDTO productoActualizar) {
        //Verificamos si el producto enviado como id existe en la base de datos
        Producto productoActualizado = productoRepository.findById(id).orElseThrow(
                () -> new RuntimeException("El producto con id " + id + " no existe")
        );

        //Actualizamos los campos del producto con los enviados en el dto
        productoActualizado.setNombre(productoActualizar.getNombre());
        productoActualizado.setDescripcion(productoActualizar.getDescripcion());
        productoActualizado.setPrecio(productoActualizar.getPrecio());
        productoActualizado.setExistencia(productoActualizar.getExistencia());
        productoActualizado.setUrlImagen(productoActualizar.getUrlImagen());

        //Validamos si la nueva categoria del producto existe en la base de datos
        Categoria categoria = categoriaRepository.findById(productoActualizar.getIdCategoria()).orElseThrow(
                () -> new RuntimeException("La nueva categoria con id " + productoActualizar.getIdCategoria() + " no existe")
        );

        //Actualizamos la categoria del producto a actualizar
        productoActualizado.setCategoria(categoria);

        //Actualizamos el producto en la base de datos y regresamos el producto en resumenDTO
        return productoMapper.toResumenDTO(productoRepository.save(productoActualizado));

    }

    @Override
    public void eliminarProducto(Long id) {
        //Verificamos si el producto con el id enviado como argumento existe en nuestra base de datos
        Producto producto = productoRepository.findById(id).orElseThrow(
                () -> new RuntimeException("El producto con el id " + id + " no existe")
        );

        //Eliminamos el producto con el id enviado como argumento
        productoRepository.deleteById(id);
    }
}
