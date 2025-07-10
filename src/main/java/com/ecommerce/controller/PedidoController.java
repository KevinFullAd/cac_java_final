package com.ecommerce.controller;

import com.ecommerce.model.Pedido;
import com.ecommerce.service.PedidoService;
import com.ecommerce.dto.PedidoRequestDTO;
import jakarta.validation.Valid;
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
    public ResponseEntity<?> crearPedido(@Valid @RequestBody PedidoRequestDTO pedidoRequest) {
        Pedido pedido = pedidoService.crearPedido(pedidoRequest.getItems());
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }


}
