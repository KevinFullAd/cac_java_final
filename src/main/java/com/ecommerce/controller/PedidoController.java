package com.ecommerce.controller;

import com.ecommerce.model.Pedido;
import com.ecommerce.service.PedidoService;
import com.ecommerce.dto.PedidoRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<Pedido>> listarPedidos() {
        List<Pedido> pedidos = pedidoService.listarPedidos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPedido(@PathVariable Long id) {
        return pedidoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Pedido>> filtrarPorEstado(@PathVariable String estado) {
        List<Pedido> pedidos = pedidoService.buscarPorEstado(estado.toUpperCase());
        return ResponseEntity.ok(pedidos);
    }


    @PostMapping
    public ResponseEntity<?> crearPedido(@RequestBody PedidoRequestDTO pedidoRequest) {
        Map<Long, Integer> items = pedidoRequest.getItems();

        if (items == null || items.isEmpty()) {
            return ResponseEntity.badRequest().body("El pedido debe contener al menos un producto.");
        }

        for (Integer cantidad : items.values()) {
            if (cantidad == null || cantidad <= 0) {
                return ResponseEntity.badRequest().body("Las cantidades deben ser mayores a cero.");
            }
        }

        try {
            Pedido pedido = pedidoService.crearPedido(items);
            return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
