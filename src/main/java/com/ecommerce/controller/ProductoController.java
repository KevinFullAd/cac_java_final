package com.ecommerce.controller;

import com.ecommerce.dto.ProductoRequestDTO;
import com.ecommerce.exception.RecursoNoEncontradoException;
import com.ecommerce.model.Producto;
import com.ecommerce.repository.ProductoRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;
    //traer todos
    @GetMapping
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    //traer por id
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerProducto(@PathVariable @Min(1) Long id) {
        return productoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto con id " + id + " no encontrado"));
    }
//    crear
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody ProductoRequestDTO dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setImagenUrl(dto.getImagenUrl());
        producto.setCategoria(dto.getCategoria()); // 👈 NO TE OLVIDES DE ESTO

        return ResponseEntity.status(201).body(productoRepository.save(producto));
    }



    //actualizar
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id,
                                        @Valid @RequestBody ProductoRequestDTO dto,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        return productoRepository.findById(id).map(producto -> {
            producto.actualizarDesdeDTO(dto);
            productoRepository.save(producto);
            return ResponseEntity.ok(producto);
        }).orElseThrow(() -> new RecursoNoEncontradoException("Producto con id " + id + " no encontrado"));
    }




    //borrar
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        return productoRepository.findById(id).map(producto -> {
            productoRepository.delete(producto);
            return ResponseEntity.noContent().build(); // O ResponseEntity.ok("Eliminado correctamente");
        }).orElseThrow(() -> new RecursoNoEncontradoException("Producto con id " + id + " no encontrado"));
    }

    //buscar
    @GetMapping("/buscar")
    public List<Producto> buscar(@RequestParam String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

}
