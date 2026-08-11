package com.spring.eccomerce.repository.specification;

import com.spring.eccomerce.entity.Producto;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductoSpecification {

    //Consulta por la categoria enviada como argumento
    public static Specification<Producto> categoriaIdEquals(Long categoriaId) {
        return (root, query, criteriaBuilder) ->
                categoriaId == null ? null : criteriaBuilder.equal(root.get("categoria").get("id"), categoriaId);
    }

    //Consulta por el nombre del producto (en minusculas)
    public static Specification<Producto> nombreLike(String nombre){
        return (root, query, criteriaBuilder) ->
                nombre == null ? null : criteriaBuilder.like
                        (criteriaBuilder.lower(root.get("nombre")), nombre.toLowerCase() + "%");
    }

    //Consulta por precio minimo o igual al enviado como argumento
    public static Specification<Producto> precioLessThanOrEqual(BigDecimal precioMinimo){
        return  (root, query, criteriaBuilder) ->
                precioMinimo == null ? null : criteriaBuilder.lessThanOrEqualTo
                        (root.get("precio"), precioMinimo);
    }

    //Consulta por precio maximo o igual al enviado como argumento
    public static Specification<Producto> precioGreaterThanOrEqual(BigDecimal precioMaximo){
        return  (root, query, criteriaBuilder) ->
                precioMaximo == null ? null : criteriaBuilder.greaterThanOrEqualTo
                        (root.get("precio"), precioMaximo);
    }

    //Consulta para obtener solo los productos que tengan mas existencias que las enviadas como argumento
    public static Specification<Producto> existenciaGreaterThan(Integer existencia){
        return (root, query, criteriaBuilder) ->
                existencia == null ? null : criteriaBuilder.greaterThan(root.get("existencia"), existencia);
    }
}
