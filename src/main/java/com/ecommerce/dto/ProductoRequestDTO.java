package com.ecommerce.dto;

import com.ecommerce.model.Categoria;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoRequestDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "La categoría es obligatoria")
    private Categoria categoria;

    @NotBlank(message = "La categoria no puede estar vacío")
    private String descripcion;

    @Min(value = 1, message = "El precio debe ser mayor que 0")
    private double precio;

    @Min(value = 0, message = "El stock no puede ser negativo")
    private int stock;

    private String imagenUrl;

    public void actualizarDesdeDTO(ProductoRequestDTO dto) {
        this.nombre = dto.getNombre();
        this.descripcion = dto.getDescripcion();
        this.precio = dto.getPrecio();
        this.stock = dto.getStock();
        this.categoria = dto.getCategoria();
    }

}
