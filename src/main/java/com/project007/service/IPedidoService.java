package com.project007.service;

import com.project007.entity.Pedido;
import com.project007.entity.TipoEntrada;
import com.project007.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface IPedidoService {

    Pedido crearPedido(Usuario asistente, List<TipoEntrada> items);

    Optional<Pedido> findById(Long id);
    
    // Este método contendrá la lógica transaccional principal
    void procesarCompraExitosa(Pedido pedido);
}
