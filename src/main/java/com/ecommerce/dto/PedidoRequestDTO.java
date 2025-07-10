package com.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = false)
@Data
public class PedidoRequestDTO {

    @NotNull(message = "Los items no pueden ser nulos.")
    @NotEmpty(message = "El pedido debe contener al menos un producto.")
    private Map<@Min(value = 1, message = "El ID del producto debe ser mayor a 0.") Long,
            @Min(value = 1, message = "La cantidad debe ser mayor a 0.") Integer> items; 
    
    public Map<Long, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Long, Integer> items) {
        this.items = items;
    }
}
