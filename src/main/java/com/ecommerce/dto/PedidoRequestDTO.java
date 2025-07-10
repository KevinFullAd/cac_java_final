package com.ecommerce.dto;

import java.util.Map;

public class PedidoRequestDTO {
    private Map<Long, Integer> items;

    public Map<Long, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Long, Integer> items) {
        this.items = items;
    }
}
