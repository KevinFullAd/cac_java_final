package com.ecommerce.repository;

import com.ecommerce.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByEstadoIgnoreCase(String estado);
}
