package com.ecommerce.service;

import com.ecommerce.exception.PedidoInvalidoException;
import com.ecommerce.exception.RecursoNoEncontradoException;
import com.ecommerce.exception.StockInsuficienteException;
import com.ecommerce.model.Pedido;
import com.ecommerce.model.Producto;
import com.ecommerce.repository.PedidoRepository;
import com.ecommerce.repository.ProductoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private ProductoRepository productoRepo;

    @Autowired
    private PedidoRepository pedidoRepo;

    public List<Pedido> listarPedidos() {
        return pedidoRepo.findAll();
    }

    public Optional<Pedido> buscarPorId(Long id) {
        return pedidoRepo.findById(id);
    }

    public List<Pedido> buscarPorEstado(String estado) {
        return pedidoRepo.findByEstadoIgnoreCase(estado);
    }

    @Transactional
    public Pedido crearPedido(Map<Long, Integer> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("El pedido debe contener al menos un producto.");
        }

        // Validar que todos los productos existan
        for (Long productoId : items.keySet()) {
            if (!productoRepo.existsById(productoId)) {
                throw new IllegalArgumentException("El producto con ID " + productoId + " no existe.");
            }
        }

        // Lógica para calcular el total, crear el pedido, etc.
        Pedido pedido = new Pedido();
        pedido.setEstado("PENDIENTE");
        pedido.setItems(items);
        pedido.setTotal(calcularTotal(items));

        return pedidoRepo.save(pedido);
    }

    private double calcularTotal(Map<Long, Integer> items) {
        double total = 0.0;
        for (Map.Entry<Long, Integer> entry : items.entrySet()) {
            Producto producto = productoRepo.findById(entry.getKey()).orElseThrow();
            total += producto.getPrecio() * entry.getValue();
        }
        return total;
    }


}
