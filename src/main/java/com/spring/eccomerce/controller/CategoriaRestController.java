package com.spring.eccomerce.controller;

import com.spring.eccomerce.dto.categoria.CategoriaRequestDTO;
import com.spring.eccomerce.dto.categoria.CategoriaResponseDTO;
import com.spring.eccomerce.dto.categoria.CategoriaResumenDTO;
import com.spring.eccomerce.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categorias")
public class CategoriaRestController {

    private final CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> crearCategoria(@Valid @RequestBody CategoriaRequestDTO nuevaCategoria) {
        return new ResponseEntity<>(categoriaService.crearCategoria(nuevaCategoria), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> actualizarCategoria(@PathVariable Long id, @Valid @RequestBody CategoriaRequestDTO nuevaCategoria) {
        return ResponseEntity.ok(categoriaService.actualizarCategoria(id, nuevaCategoria));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResumenDTO>> listarCategorias() {
        return ResponseEntity.ok(categoriaService.obtenerCategorias());
    }
}
