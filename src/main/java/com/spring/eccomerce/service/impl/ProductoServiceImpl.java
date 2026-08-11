package com.spring.eccomerce.service.impl;

import com.spring.eccomerce.config.StorageProperties;
import com.spring.eccomerce.dto.producto.*;
import com.spring.eccomerce.entity.Categoria;
import com.spring.eccomerce.entity.Producto;
import com.spring.eccomerce.exception.CategoriaNotFoundException;
import com.spring.eccomerce.exception.ProductoDuplicadoException;
import com.spring.eccomerce.exception.ProductoNotFoundException;
import com.spring.eccomerce.mapper.ProductoMapper;
import com.spring.eccomerce.repository.CategoriaRepository;
import com.spring.eccomerce.repository.ProductoRepository;
import com.spring.eccomerce.repository.specification.ProductoSpecification;
import com.spring.eccomerce.service.ProductoService;
import com.spring.eccomerce.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    //Dependencia del servicio de storage para obtener la url de la imagen del producto y guardala
    private final StorageService storageService;

    @Override
    public Page<ProductoResumenDTO> obtenerProductos(ProductoFiltroDTO filtroDTO, Pageable pagina) {
        //Generemos la consulta dinmica con las especificaciones enviadas como parametro dentro del dto
        Specification<Producto> especificaciones = Specification.where(ProductoSpecification.categoriaIdEquals(filtroDTO.getCategoriaId()))
                .and(ProductoSpecification.nombreLike(filtroDTO.getNombre()))
                .and(ProductoSpecification.precioGreaterThanOrEqual(filtroDTO.getPrecioMaximo()))
                .and(ProductoSpecification.precioLessThanOrEqual(filtroDTO.getPrecioMinimo()))
                .and(ProductoSpecification.existenciaGreaterThan(filtroDTO.getExistencia()));

        //Obtenemos la pagina de productos con las especificaciones indicadas y convertimos los productos de la pagina
        //en resumenes de productos
        return productoRepository.findAll(especificaciones, pagina).map(productoMapper::toResumenDTO);
    }

    @Override
    public ProductoDetalleDTO obtenerProductoPorId(Long id) {
        //Verificamos si el producto con el id enviado como argumento existe en la base de datos
        Producto producto = productoRepository.findById(id).orElseThrow(
                () -> new ProductoNotFoundException(id)
        );

        //Si existe, regresamos el producto del id como responseDTO
        return productoMapper.toDetalleDTO(producto);
    }

    @Override
    public ProductoResumenDTO crearProducto(ProductoRequestDTO producto, MultipartFile imagen) {
        //Verificamos si ya existe un producto en la base de datos con el nombre del producto que se quiere agregar
        if (productoRepository.existsByNombreIgnoreCase(producto.getNombre())) {
            throw new ProductoDuplicadoException(producto.getNombre());
        }

        //Convertimos el dto enviado como argumento a una entidad de producto
        Producto productoNuevo = productoMapper.toEntity(producto);

        //Obtenemos la categoria enviada como campo de dto que pertenece al producto
        Categoria categoria = categoriaRepository.findById(producto.getIdCategoria()).orElseThrow(
                () -> new CategoriaNotFoundException("La categoria con id " + producto.getIdCategoria() + " asociada al producto, no existe")
        );

        //Le asignamos la categoria a la entidad producto que acabamos de generar
        productoNuevo.setCategoria(categoria);

        //Obtenemos la url de la imagen enviada como argumento a la vez que la almacenamos en el directorio de uploads
        String urlImagen = storageService.guardar(imagen);

        //Establecemos la url de la imagen
        productoNuevo.setUrlImagen(urlImagen);

        //Guardamos en la base de datos el producto y regresamos el producto creado como resumenDTO
        return productoMapper.toResumenDTO(productoRepository.save(productoNuevo));
    }

    @Override
    public void actualizarProducto(Long id, ProductoRequestDTO productoActualizar, MultipartFile imagen) {
        //Verificamos si el producto enviado como id existe en la base de datos
        Producto productoActualizado = productoRepository.findById(id).orElseThrow(
                () -> new ProductoNotFoundException(id)
        );

        //Si se envio una nueva imagen para el producto
        if (!imagen.isEmpty()){
            //Obtenemos la url de la imagen que tenia el producto
            String urlImagenAntigua = productoActualizado.getUrlImagen();

            //Generamos la url de la imagen enviada como parametro y la establecemos como la imagen del producto
            productoActualizado.setUrlImagen(storageService.guardar(imagen));

            //Borramos la imagen antigua que tenia el producto
            storageService.eliminar(urlImagenAntigua);
        }

        //Actualizamos los campos del producto con los enviados en el dto
        productoActualizado.setNombre(productoActualizar.getNombre());
        productoActualizado.setDescripcion(productoActualizar.getDescripcion());
        productoActualizado.setPrecio(productoActualizar.getPrecio());
        productoActualizado.setExistencia(productoActualizar.getExistencia());

        //Validamos si la nueva categoria del producto existe en la base de datos
        Categoria categoria = categoriaRepository.findById(productoActualizar.getIdCategoria()).orElseThrow(
                () -> new CategoriaNotFoundException("La nueva categoria con id " + productoActualizar.getIdCategoria() + " no existe")
        );

        //Actualizamos la categoria del producto a actualizar
        productoActualizado.setCategoria(categoria);

        //Actualizamos el producto en la base de datos y regresamos el producto en resumenDTO
        productoMapper.toResumenDTO(productoRepository.save(productoActualizado));

    }

    @Override
    public List<ProductoResumenDTO> obtenerProductosDestacados() {
        //Regresamos la lista de productos destacados en el formato de resumenDTO del producto
        return productoRepository.findTop8ByOrderByIdDesc().stream().map(productoMapper::toResumenDTO).toList();
    }

    @Override
    public void eliminarProducto(Long id) {
        //Verificamos si el producto con el id enviado como argumento existe en nuestra base de datos
        Producto producto = productoRepository.findById(id).orElseThrow(
                () -> new ProductoNotFoundException(id)
        );

        //Eliminamos del directorio uploads la imagen del producto
        storageService.eliminar(producto.getUrlImagen());

        //Eliminamos el producto con el id enviado como argumento
        productoRepository.deleteById(id);
    }

    @Override
    public ProductoRequestDTO obtenerProductoEditar(Long id){
        //Obtenemos el producto que se quiere editar por el id enviado como argumento
        ProductoDetalleDTO productoEditar = obtenerProductoPorId(id);

        //Regresamos el producto que se quiere editar en el formato de requestDTO
        return productoMapper.toRequestDTO(productoEditar);
    }
}
