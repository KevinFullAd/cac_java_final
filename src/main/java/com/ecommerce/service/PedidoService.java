package com.ecommerce.service;

import com.ecommerce.exception.RecursoNoEncontradoException;
import com.ecommerce.exception.StockInsuficienteException;
import com.ecommerce.model.Pedido;
import com.ecommerce.model.Producto;
import com.ecommerce.repository.PedidoRepository;
import com.ecommerce.repository.ProductoRepository;
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


    public Pedido crearPedido(Map<Long, Integer> items) {
        double total = 0;

        for (Map.Entry<Long, Integer> entry : items.entrySet()) {
            Long productoId = entry.getKey();
            Integer cantidad = entry.getValue();

            Producto producto = productoRepo.findById(productoId)
                    .orElseThrow(() -> new RecursoNoEncontradoException("Producto ID " + productoId + " no encontrado"));

            if (producto.getStock() < cantidad) {
                throw new StockInsuficienteException("Stock insuficiente para " + producto.getNombre());
            }

            producto.setStock(producto.getStock() - cantidad);
            productoRepo.save(producto); // actualiza stock

            total += producto.getPrecio() * cantidad;
        }

        Pedido pedido = new Pedido();
        pedido.setItems(items);
        pedido.setTotal(total);
        pedido.setEstado("PENDIENTE");

        return pedidoRepo.save(pedido);
    }
}
