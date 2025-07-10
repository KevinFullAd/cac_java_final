package com.ecommerce.exception;

public class PedidoInvalidoException extends RuntimeException {
    public PedidoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
