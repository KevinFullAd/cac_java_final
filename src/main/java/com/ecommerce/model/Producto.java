package com.ecommerce.model;

import com.ecommerce.dto.ProductoRequestDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcion;

    @Min(value = 1, message = "El precio debe ser mayor que 0")
    private double precio;

    @Min(value = 0, message = "El stock no puede ser negativo")
    private int stock;

    @NotBlank(message = "La URL de la imagen no puede estar vacía")
    private String imagenUrl;

    @NotNull(message = "La categoría es obligatoria")
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    public void actualizarProducto(Producto p) {
        this.nombre = p.getNombre();
        this.descripcion = p.getDescripcion();
        this.precio = p.getPrecio();
        this.stock = p.getStock();
        this.imagenUrl = p.getImagenUrl();
        this.categoria = p.getCategoria();
    }

    public void actualizarDesdeDTO(ProductoRequestDTO dto) {
        this.nombre = dto.getNombre();
        this.descripcion = dto.getDescripcion();
        this.precio = dto.getPrecio();
        this.stock = dto.getStock();
        this.categoria = dto.getCategoria();
    }

}
